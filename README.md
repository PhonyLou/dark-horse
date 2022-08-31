# dark-horse

## Task
- http
- database(pg)
- MQ
- docker-compose

## Setup

The project requires [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or
higher.

The project makes use of Gradle and uses
the [Gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html), which means you don't need Gradle
installed.

Install database and run for local use and test. 
```shell
$ brew install postgresql
$ brew services start postgresql
$ psql postgres -c 'create user postgres createdb'
$ psql -c 'create database demo;' -U postgres
```

## Useful Gradle commands

The project makes use of Gradle and uses the Gradle wrapper to help you out carrying some common tasks such as building
the project or running it.

### List all Gradle tasks

List all the tasks that Gradle can do, such as `build` and `test`.

```console
$ ./gradlew tasks
```

### Build the project

Compiles the project, runs the test and then creates an executable JAR file

```console
$ ./gradlew build
```

Run the application using Java and the executable JAR file produced by the Gradle `build` task. The application will be
listening to port `8080`.

```console
$ java -jar build/libs/joi-energy.jar
```

### Run the tests

There are two types of tests, the unit tests and the functional tests. These can be executed as follows.

- Run unit tests only

  ```console
  $ ./gradlew test
  ```

### Run the application

Run the application which will be listening on port `8080`.

```console
$ ./gradlew bootRun
```
