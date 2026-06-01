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
1. Configure PostgreSQL and create a database.
2. Update `src/main/resources/db.properties` with your connection details.
3. Build with `mvn clean package`.
4. Deploy the generated WAR to Tomcat.

## Next steps
- Add authentication and DTO validation.
- Implement DAO logic.
- Expand UI forms for project creation and editing.
