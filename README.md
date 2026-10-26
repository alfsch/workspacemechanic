# Workspace Mechanic for Eclipse™

[![Build](https://github.com/alfsch/workspacemechanic/actions/workflows/build.yaml/badge.svg)](https://github.com/alfsch/workspacemechanic/actions/workflows/build.yaml)
[![Release](https://img.shields.io/github/v/release/alfsch/workspacemechanic)](https://github.com/alfsch/workspacemechanic/releases/latest)
[![Eclipse Marketplace](https://img.shields.io/eclipse-marketplace/v/workspace-mechanic)](https://marketplace.eclipse.org/content/workspace-mechanic)
[![License: EPL-1.0](https://img.shields.io/github/license/alfsch/workspacemechanic)](LICENSE)
![Java 17](https://img.shields.io/badge/Java-17-blue)

Workspace Mechanic keeps your Eclipse settings consistent. You describe preferences and key bindings in task files, which can be checked into your team's SCM. The plug-in checks your workspace against them in the background, shows a status icon in the workbench, and applies the differences with one click.

It is the maintained continuation of the [Google Code project](https://code.google.com/archive/a/eclipselabs.org/p/workspacemechanic).

## Install
Requires Java 17 (Eclipse has to run on Java 17 or newer).

- From the [Eclipse Marketplace](https://marketplace.eclipse.org/content/workspace-mechanic), or
- in Eclipse with *Help > Install New Software... > Add...* and the update site `https://alfsch.github.io/eclipse-updates/workspacemechanic`, or
- from the update site zip attached to each [release](https://github.com/alfsch/workspacemechanic/releases) (*Add... > Archive...*).

## Getting started
1. After the restart, a Workspace Mechanic icon appears in the status bar of the workbench: a green tick when all tasks are in sync, a warning when a task needs to be applied.
2. Put task files into a task source. There are two kinds of tasks:
   - `.epf` files: Eclipse preference exports (*File > Export > General > Preferences*), applied as preferences. Optional header comments at the top of the file say how the task behaves:
     ```
     # @title Team code style
     # @description Formatter and save actions for all workspaces
     # @task_type RECONCILE
     #
     ```
     With `@task_type LASTMOD` (the default when there is no header) the task is applied again only when the *file* changes, so settings you change by hand in Eclipse afterwards are not noticed. With `@task_type RECONCILE` the task fails whenever any preference in the file differs from your running Eclipse, so hand-made changes show up as a warning and the fix resets them. The Preference Recorder writes this header for you.
   - `.kbd` files: key binding changes.
3. Tell Workspace Mechanic where the task sources are: *Preferences > Workspace Mechanic > Task sources*. A task source is a directory or a URL. By default the directory `~/.eclipse/mechanic` is used (see the field for the full default list). In the same page you can set the scan frequency (default: one hour), block single tasks and turn off the popup that appears when tasks fail.
4. Click the status icon to apply the fixes, or use its context menu:
   - **Check All**: scan the task sources now.
   - **Apply Fixes...**: choose which failing tasks to apply or block.
   - **Re-apply All Tasks...**: apply every task again, also those that are already in sync, for example `LASTMOD` tasks after someone changed settings by hand (blocked tasks are skipped).
   - **Preference Recorder**: record the preference changes you make and save them as an `.epf` task file.
   - **Show all tasks**, **Dump keybindings...**, **Stop Service** / **Start Service**, and a link to the preferences.

## Building
The build is fully maven based and comes with the Maven Wrapper, so only a JDK 21 is needed to build (the plug-in itself is compiled for Java 17 and runs on Java 17 or newer): the wrapper downloads Maven, and its version is set in `.mvn/wrapper/maven-wrapper.properties`. Build it with `./mvnw -f parent/pom.xml clean install` (`mvnw.cmd` on Windows) or the corresponding m2e command. This builds the plugins, features and the update site.

PMD, Checkstyle and SpotBugs run in the `verify` phase of the plugin and the samples and fail the build on findings that are not in the baseline in `parent/analysis` (the known findings, one entry per class or file and rule). Fix a finding and run `python3 parent/analysis/update-baseline.py` to remove it from the baseline, never to hide a new one. `-Danalysis.fail=false` only reports the findings. A baseline entry covers every finding of that rule in that class or file, so a second finding of the same rule in a listed class or file is not caught. The CI run uploads the reports as the `analysis-reports` artifact.

## Next Steps are
1. Fix the SpotBugs findings and decide on the rules to use
2. Fix the PMD findings and decide on the rules to use
3. Fix the Checkstyle findings and decide on the rules to use (it runs with the Google style, which the code does not follow yet)
4. Dokumentation in Wiki
5. Re-enable the SonarQube analysis (commented out in `parent/pom.xml`, the old Sonar server is gone) and find a Sonar server solution

Done: the build runs on JDK 21 and produces Java 17 bytecode, uses Tycho 4 and the Eclipse 2024-12 target platform.

and not to forget

**Bugfixing**


## Releasing
1. The release version is the project version without `-SNAPSHOT` (`0.6.1-SNAPSHOT` is released as `v0.6.1`).
2. Push a tag `v<version>`, e.g. `v0.6.1`.
3. The `Release` workflow builds the update site, creates a GitHub Release with the update site zip and, after approval of the `update-site` environment, publishes it to the update site above.
4. Afterwards move to the next snapshot version in a pull request. The next version is normally the next patch version (`0.6.1` is followed by `0.6.2-SNAPSHOT`), also when it collects many dependency updates; a new minor version is for real new features. This updates the poms, the manifests, `feature.xml` and `category.xml`:
   `./mvnw -f parent/pom.xml org.eclipse.tycho:tycho-versions-plugin:4.0.10:set-version -DnewVersion=0.6.2-SNAPSHOT`

The update site is a p2 composite repository in the repository `alfsch/alfsch.github.io` (folder `eclipse-updates/workspacemechanic`). It has one folder per release (`0.6.1/`, ...) plus `legacy/` for everything published before, and a release only adds a folder, so older versions stay installable. `.github/scripts/update-site.py` does this. To publish a release that already has a GitHub Release without tagging again, run the `Release` workflow by hand and enter its tag.

One-time setup: add a deploy key with write access to `alfsch/alfsch.github.io` and store its private key as the repository secret `UPDATE_SITE_DEPLOY_KEY`; optionally add required reviewers to the `update-site` environment.

## Trademarks
Eclipse™ and Eclipse IDE™ are trademarks of Eclipse Foundation AISBL. Workspace Mechanic is an independent project and is not affiliated with or endorsed by the Eclipse Foundation.
