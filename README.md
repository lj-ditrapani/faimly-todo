Family ToDo
===========

Multi-user LAN todo.
Client: android kotlin
Server: vert.x kotlin http server


Backend
=======


Build
-----

    cd backend
    ./gradlew build


Run
---

    ./gradlew installDist
    ./build/install/todo/bin/todo


Develop
-------

Format code

    ./gradlew ktlintFormat

Run unit tests

    ./gradlew test

Run test coverage report

    ./gradlew test jacocoTestReport
    firefox build/reports/jacoco/test/html/index.html

Lint, test, run

    ./gradlew ktlintFormat test run


Client
======
