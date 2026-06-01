# Shared Code Notebook

A simple Java web application for managing development projects and programming languages.

## Stack
- Java Servlet
- JSP
- Bootstrap
- PostgreSQL
- Maven
- Apache Tomcat

## Project Structure
- `src/main/java` - Java source code
- `src/main/resources` - configuration files
- `src/main/webapp` - JSP views and static assets

## Setup
1. Create a PostgreSQL database named `shared_code_notebook` (or another name of your choice).
2. Run the statements in `schema.sql` to create the tables.
3. Update `src/main/resources/db.properties` with your database URL, username, and password.
4. Build the project with:
   ```bash
   mvn clean package
   ```
5. Deploy `target/shared-code-notebook.war` to Apache Tomcat.

## GitHub Upload
1. Create a new repository on GitHub named `shared-code-notebook`.
2. Add the remote to your local repository:
   ```bash
   git remote add origin https://github.com/<your-username>/shared-code-notebook.git
   git branch -M main
   git push -u origin main
   ```

## Next steps
- Complete the DAO implementations for projects and programming languages.
- Add project CRUD servlets and JSP pages.
- Enable authentication protection for dashboard pages.
- Improve the UI with Bootstrap cards and forms.
