# Review instructions

Eclipse Workspace Mechanic: an Eclipse plugin built with Maven/Tycho 4 on Java 17 through the Maven Wrapper (`./mvnw -f parent/pom.xml clean install`; the Maven version is in `.mvn/wrapper/maven-wrapper.properties`).

- Modules live under `parent/`: `bundles`, `features`, `tests`, `releng`, `config`, `examples`.
- Bundles target Java 17 (source/target in `parent/pom.xml`).
- Keep `MANIFEST.MF`, `feature.xml` and `pom.xml` versions and dependencies consistent when one of them changes.
- Dependencies are copied into `lib/` at build time (`copy-dependencies` in the `initialize` phase); jars listed in `build.properties` and `Bundle-ClassPath` must match the copied file names.
- Flag likely FindBugs/PMD/Checkstyle issues, resource leaks, and UI code touching SWT widgets off the UI thread.
- Don't comment on formatting alone.
