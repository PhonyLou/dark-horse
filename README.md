# dark-horse

## Task
- http Client
- database(pg) OK
- MQ OK
- docker-compose OK

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

## Run tests

### precondition

Setup message queue by using docker-compose command.
You can manage it via `http://localhost:15673/`. Create a queue named `demoQueue` from the mq management dashboard.
```shell
$ docker-compose up rabbitmq
```

### Run unit tests only
```shell
$ ./gradlew clean test
```

## Build app
Compiles the project, runs the test and then creates an executable JAR file

```console
$ ./gradlew build
```

### Run app
Run the application which will be listening on port `8080`.

```shell
$ ./gradlew bootRun
```

Run the application using Java and the executable JAR file produced by the Gradle `build` task. The application will be
listening to port `8080`.
