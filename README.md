# Supermarket Management System

This project contains a Java Swing GUI application with Admin and Customer panels,
and uses MySQL (JDBC) as the backend.



## What is included
- `src/` - Java source code (package `app`, `app.ui`, `app.dao`, `app.models`, `app.util`)
- `sql/supermarket.sql` - SQL script to create database & seed data
- `build.gradle` - Gradle build file that fetches the MySQL connector and creates a fat JAR
- `run.sh` / `run.bat` - convenience scripts to build and run the app
- `lib/` - empty placeholder (you may put `mysql-connector-java.jar` here if you prefer)

## Important notes
- I could not include the MySQL Connector/J JAR or compile the project in this environment.
- The provided `build.gradle` will download the MySQL driver from Maven Central when you run Gradle locally.
- To build and run a runnable JAR (fat jar) locally:

### Using Gradle (recommended)
1. Install Gradle (or use the Gradle wrapper if you add it).
2. From the project root run:
   ```
   gradle clean shadowJar
   ```
   This will produce a fat JAR in `build/libs/SupermarketManagementSystem-all.jar`.

3. Run the JAR:
   ```
   java -jar build/libs/SupermarketManagementSystem-all.jar
   ```

### Alternative: Using provided `lib/`
- If you already have `mysql-connector-java-<version>.jar`, place it in `lib/` and build with your IDE (IntelliJ/Eclipse) ensuring the jar is on the classpath.

## Database setup
1. Start MySQL server.
2. Run `sql/supermarket.sql` (e.g. `mysql -u root -p < sql/supermarket.sql`)
3. Ensure credentials in `src/app/DBConnection.java` match your MySQL setup.

## GUI login
- Admin demo credentials: `admin` / `admin123`
- Customer: open Customer panel without login.

If you want, I can next:
- add the Gradle wrapper files so building only requires `./gradlew`,
- or produce a precompiled JAR if you upload the MySQL connector jar here so I can include it.