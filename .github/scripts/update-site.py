#!/usr/bin/env python3
"""Maintains the Workspace Mechanic p2 update site as a composite repository.

The site root only holds a composite repository (compositeContent.xml,
compositeArtifacts.xml, p2.index) that lists one child folder per release, so a
release is added without touching the older ones.

  update-site.py migrate SITE_DIR
      One time: moves a plain p2 repository in SITE_DIR into SITE_DIR/legacy and
      turns SITE_DIR into a composite repository with that single child.

  update-site.py add SITE_DIR VERSION REPOSITORY_DIR
      Copies the p2 repository REPOSITORY_DIR to SITE_DIR/VERSION and adds it as a
      child. Refuses to replace an existing release folder.
"""
import html
import lzma
import os
import re
import shutil
import sys
import time
import zipfile

NAME = "Workspace Mechanic Updates"
LEGACY = "legacy"
PLAIN_REPOSITORY_FILES = [
    "artifacts.jar", "artifacts.xml.xz", "content.jar", "content.xml.xz",
    "features", "plugins", "p2.index", "index.html",
]

COMPOSITE_TEMPLATE = """<?xml version='1.0' encoding='UTF-8'?>
<?{pi} version='1.0.0'?>
<repository name='{name}' type='{type}' version='1.0.0'>
  <properties size='2'>
    <property name='p2.compressed' value='false'/>
    <property name='p2.timestamp' value='{timestamp}'/>
  </properties>
  <children size='{size}'>
{children}
  </children>
</repository>
"""

P2_INDEX = """version=1
metadata.repository.factory.order=compositeContent.xml,\\!
artifact.repository.factory.order=compositeArtifacts.xml,\\!
"""

INDEX_HTML = """<!DOCTYPE html>
<html lang="en">
<head><meta charset="utf-8"><title>{name}</title></head>
<body>
<h1>{name}</h1>
<p>This is a p2 update site. Add this URL in Eclipse under <em>Help &gt; Install New Software</em>.
For information about installing or updating software, see the
<a href="https://help.eclipse.org/latest/topic/org.eclipse.platform.doc.user/tasks/tasks-124.htm">Eclipse Platform Help</a>.</p>
<h2>Releases</h2>
<ul>
{items}
</ul>
</body>
</html>
"""


def version_key(name):
    # legacy first, then releases ascending by their numeric parts
    if name == LEGACY:
        return (0,)
    return (1,) + tuple(int(p) for p in re.findall(r"\d+", name))


def read_children(site):
    path = os.path.join(site, "compositeContent.xml")
    with open(path, encoding="utf-8") as f:
        return re.findall(r"<child location='([^']+)'/>", f.read())


def write_site_files(site, children):
    children = sorted(set(children), key=version_key)
    lines = "\n".join("    <child location='%s'/>" % html.escape(c, quote=True) for c in children)
    timestamp = int(time.time() * 1000)
    for filename, pi, kind in (
        ("compositeContent.xml", "compositeMetadataRepository",
         "org.eclipse.equinox.internal.p2.metadata.repository.CompositeMetadataRepository"),
        ("compositeArtifacts.xml", "compositeArtifactRepository",
         "org.eclipse.equinox.internal.p2.artifact.repository.CompositeArtifactRepository"),
    ):
        with open(os.path.join(site, filename), "w", encoding="utf-8") as f:
            f.write(COMPOSITE_TEMPLATE.format(
                pi=pi, name=NAME, type=kind, timestamp=timestamp,
                size=len(children), children=lines))
    with open(os.path.join(site, "p2.index"), "w", encoding="utf-8") as f:
        f.write(P2_INDEX)
    items = "\n".join('<li><a href="%s/">%s</a></li>' % (c, c) for c in reversed(children))
    with open(os.path.join(site, "index.html"), "w", encoding="utf-8") as f:
        f.write(INDEX_HTML.format(name=NAME, items=items))


def write_p2_index(repository, metadata, artifacts):
    """p2.index decides which metadata files a client reads first."""
    with open(os.path.join(repository, "p2.index"), "w", encoding="utf-8") as f:
        f.write("version=1\nmetadata.repository.factory.order=%s,\\!\n"
                "artifact.repository.factory.order=%s,\\!\n" % (metadata, artifacts))


def use_jar_metadata(repository):
    """For the old repository: content.jar and artifacts.jar hold every published
    version, the .xz files (read first by p2) only the newest. Keep the complete ones."""
    for name in ("content.xml.xz", "artifacts.xml.xz"):
        path = os.path.join(repository, name)
        if os.path.exists(path):
            os.remove(path)
    # "content.xml,\!" is the usual form: after the (absent) plain file p2 falls back to its
    # default order, which finds the .jar files
    write_p2_index(repository, "content.xml", "artifacts.xml")


def use_xz_metadata(repository):
    """For a new release: Tycho writes .xz metadata that describes exactly this build.
    Stale .jar metadata (an old mirror step appended other versions to it) is dropped,
    and so are the bundle and feature jars the metadata does not mention."""
    artifacts_xz = os.path.join(repository, "artifacts.xml.xz")
    if not os.path.exists(artifacts_xz):
        return
    for name in ("content.jar", "artifacts.jar"):
        path = os.path.join(repository, name)
        if os.path.exists(path):
            os.remove(path)
    write_p2_index(repository, "content.xml.xz", "artifacts.xml.xz")
    with lzma.open(artifacts_xz) as f:
        text = f.read().decode("utf-8")
    wanted = {"osgi.bundle": "plugins", "org.eclipse.update.feature": "features"}
    expected = set()
    for classifier, id_, version in re.findall(
            r"<artifact classifier='([^']+)' id='([^']+)' version='([^']+)'", text):
        if classifier in wanted:
            expected.add(os.path.join(wanted[classifier], "%s_%s.jar" % (id_, version)))
    for folder in wanted.values():
        directory = os.path.join(repository, folder)
        if not os.path.isdir(directory):
            continue
        for name in os.listdir(directory):
            relative = os.path.join(folder, name)
            if relative not in expected:
                os.remove(os.path.join(repository, relative))
                print("removed %s: not part of this release" % relative)


def check_repository(path):
    has_content = any(os.path.exists(os.path.join(path, n))
                      for n in ("content.jar", "content.xml", "content.xml.xz"))
    has_artifacts = any(os.path.exists(os.path.join(path, n))
                        for n in ("artifacts.jar", "artifacts.xml", "artifacts.xml.xz"))
    if not (has_content and has_artifacts):
        sys.exit("%s is not a p2 repository (content or artifacts metadata missing)" % path)


def migrate(site):
    if os.path.exists(os.path.join(site, "compositeContent.xml")):
        sys.exit("%s already is a composite repository" % site)
    check_repository(site)
    legacy = os.path.join(site, LEGACY)
    os.makedirs(legacy)
    for name in PLAIN_REPOSITORY_FILES:
        src = os.path.join(site, name)
        if os.path.exists(src):
            shutil.move(src, os.path.join(legacy, name))
    use_jar_metadata(legacy)
    check_repository(legacy)
    write_site_files(site, [LEGACY])
    print("migrated %s: plain repository moved to %s/" % (site, LEGACY))


def add(site, version, repository):
    if not re.fullmatch(r"\d+(\.\d+)*", version):
        sys.exit("version must look like 0.6.1, got %r" % version)
    if not os.path.exists(os.path.join(site, "compositeContent.xml")):
        sys.exit("%s is not a composite repository yet, run migrate first" % site)
    check_repository(repository)
    target = os.path.join(site, version)
    if os.path.exists(target):
        sys.exit("release %s already exists in %s, releases are not replaced" % (version, site))
    children = read_children(site)
    shutil.copytree(repository, target)
    use_xz_metadata(target)
    check_repository(target)
    write_site_files(site, children + [version])
    print("added %s, children now: %s" % (version, ", ".join(sorted(set(children + [version]), key=version_key))))


def main(argv):
    if len(argv) == 3 and argv[1] == "migrate":
        migrate(argv[2])
    elif len(argv) == 5 and argv[1] == "add":
        add(argv[2], argv[3], argv[4])
    else:
        sys.exit(__doc__)


if __name__ == "__main__":
    main(sys.argv)
