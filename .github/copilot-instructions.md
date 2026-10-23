# Review instructions

Eclipse Workspace Mechanic: an Eclipse plugin built with Maven/Tycho 0.25 (`mvn -f parent/pom.xml clean install`).

- Modules live under `parent/`: `bundles`, `features`, `tests`, `releng`, `config`, `examples`.
- Bundles target Java 1.7 (source/target in `parent/pom.xml`); flag use of Java 8+ APIs or language features.
- Keep `MANIFEST.MF`, `feature.xml` and `pom.xml` versions and dependencies consistent when one of them changes.
- Flag likely FindBugs/PMD/Checkstyle issues, resource leaks, and UI code touching SWT widgets off the UI thread.
- Don't comment on formatting alone.
