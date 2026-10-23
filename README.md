# Eclipse Workspace Mechanic

[![Build](https://github.com/alfsch/workspacemechanic/actions/workflows/build.yaml/badge.svg)](https://github.com/alfsch/workspacemechanic/actions/workflows/build.yaml)
[![Release](https://img.shields.io/github/v/release/alfsch/workspacemechanic)](https://github.com/alfsch/workspacemechanic/releases/latest)
[![Eclipse Marketplace](https://img.shields.io/eclipse-marketplace/v/workspace-mechanic)](https://marketplace.eclipse.org/content/workspace-mechanic)
[![License: EPL-1.0](https://img.shields.io/github/license/alfsch/workspacemechanic)](LICENSE)
![Java 17](https://img.shields.io/badge/Java-17-blue)

New Repo for workspace mechanic, overtaken [from google code](https://code.google.com/archive/a/eclipselabs.org/p/workspacemechanic)

Workspace mechanic build is full maven based. Build it with mvn clean install or the corresponding m2e command.

This command builds the plugins, features and the update site. It needs Java 17 and Maven 3.9 or newer.

## Update Site

https://alfsch.github.io/eclipse-updates/workspacemechanic

The plugin is also listed on the [Eclipse Marketplace](https://marketplace.eclipse.org/content/workspace-mechanic).

## Next Steps are
1. Fix Findbugs and used rules
2. Fix PMD's and used rules
3. Fix Checkstyles and used rules
4. Dokumentation in Wiki
5. Re-enable the SonarQube analysis (commented out in `parent/pom.xml`, the old Sonar server is gone) and find a Sonar server solution

Done: the build uses Java 17, Tycho 4 and the Eclipse 2024-12 target platform.

and not to forget

**Bugfixing**


## Releasing
1. The release version is the project version without `-SNAPSHOT` (`0.6.0-SNAPSHOT` is released as `v0.6.0`).
2. Push a tag `v<version>`, e.g. `v0.6.0`.
3. The `Release` workflow builds the update site, creates a GitHub Release with the update site zip and, after approval of the `update-site` environment, publishes it to the update site above.
4. Afterwards move to the next snapshot version in a pull request. This updates the poms, the manifests, `feature.xml` and `category.xml`:
   `mvn -f parent/pom.xml org.eclipse.tycho:tycho-versions-plugin:4.0.10:set-version -DnewVersion=0.7.0-SNAPSHOT`

One-time setup: add a deploy key with write access to `alfsch/alfsch.github.io` and store its private key as the repository secret `UPDATE_SITE_DEPLOY_KEY`; optionally add required reviewers to the `update-site` environment.
