# Kalaha back-end

The back-end is build with [Spring Boot 2](https://spring.io/projects/spring-boot)
and [Apache Maven](https://maven.apache.org/), with a dependency on a [PostgresQL](https://www.postgresql.org/)
database.

## Prerequisites

To start developing and to run the application you will need:

- JDK 11+, for instance [OpenJDK 11](https://openjdk.java.net/projects/jdk/11/)
- Apache Maven 3.3+

A useful tool to install and manage JDK-versions can be [sdkman](https://sdkman.io/)
Also
see [this link](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started-system-requirements)
for Spring Boot system requirements.

## Install the app

You can install the project by running `mvn clean install` in the back-end root folder.  
This will create an executable jar under the `target` folder.

## Running the application

Spring Boot Web runs the application by default in
an [Embedded Tomcat container](https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto-embedded-web-servers)  
After installing the project you can run the back-end in two ways:

### Test/Development mode

Use: local testing and development  
Database: in-memory [H2](https://www.h2database.com)  
How to run: open a terminal, go to the back-end root folder and
run `java -jar -Dspring.profiles.active=test target/kalaha.jar` or `mvn spring-boot:run -Dspring-boot.run.profiles=test`

### Production mode

Use: deployment and production like environment  
Database: PostgresQL [latest](https://registry.hub.docker.com/_/postgres) docker image, set-up
with [Docker Compose](https://docs.docker.com/compose/) based on the `docker-compose.yml` which can be found in the root
folder of the back-end.  
How to run: open a terminal, go to the back-end root folder and run `docker-compose up && java -jar target/kalaha.jar`
or `docker-compose up && mvn spring-boot:run`

## Running Unit tests

The unit tests are made with [JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
and [Mockito](https://site.mockito.org/).  
They can be run by executing `mvn clean verify` in the root folder of the back-end.  
To see the code coverage you can run `mvn clean verify jacoco:report`, which will create a code coverage report
in `target/site/jacoco/` folder in the root folder of the back-end with an `index.html` file which you can view in your
browser. See [this link about Jacoco](https://www.baeldung.com/jacoco) for more information.

## Running integration tests

The integration tests are made with [Cucumber](https://cucumber.io/), based
on [this article](https://thepracticaldeveloper.com/2018/03/31/cucumber-tests-spring-boot-dependency-injection/).  
All Unit tests are written in JUnit 5. Unfortunately, Cucumber does not support this yet and is written in Junit 4.  
One of the improvements, which can be found under `documentation/IMPROVEMENTS.md` for the project is to run the
integration tests with Maven. This is a problem that is not yet solved.  
This means that currently the only proven way to run the tests is by running/selecting them manually
in [Intellij](https://www.jetbrains.com/idea/)

## Further help

To get more help on the Maven CLI use `mvn help:help`.
