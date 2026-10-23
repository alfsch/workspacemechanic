# Eclipse Workspace Mechanic
New Repo for workspace mechanic, overtaken [from google code](https://code.google.com/archive/a/eclipselabs.org/p/workspacemechanic)

Workspace mechanic build is full maven based. Build it with mvn clean install or the corresponding m2e command.

This command builds the plugins, features and the update site. It needs Java 17 and Maven 3.9 or newer.

## Update Site

https://alfsch.github.io/eclipse-updates/workspacemechanic

## Next Steps are
1. Fix Findbugs and used rules
2. Fix PMD's and used rules
3. Fix Checkstyles and used rules
4. Dokumentation in Wiki
5. Re-enable the SonarQube analysis (commented out in `parent/pom.xml`, the old Sonar server is gone) and find a Sonar server solution

Done: the build uses Java 17, Tycho 4 and the Eclipse 2024-12 target platform.

and not to forget

**Bugfixing**


## Build Status
[![Build](https://github.com/alfsch/workspacemechanic/actions/workflows/build.yaml/badge.svg)](https://github.com/alfsch/workspacemechanic/actions/workflows/build.yaml)
