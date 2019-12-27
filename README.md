Family ToDo
===========

Multi-user LAN todo.
- Client: android kotlin
- Server: vert.x kotlin http server


Client
======

My first android app.
I made this project to learn
- Fragment
- NavHostFragment
- NavController
- NavGraph
- RecyclerView
- ViewModel
- Dagger
- LiveData
- MVVM

Fragments
- Splash
- Setup
- Todo view
    - can mark items as done here
    - Menu at bottom to Setup | Add | Clean | Order | Refresh
- Add view
- Prioritize view


Backend
=======

Build & run dependecies: [docker](https://www.docker.com/)


Build
-----

    cd backend
    sh build.sh

Run
---

    sh docker-run.sh


Develop
-------

Develop dependecies: [java 11.0.5](https://sdkman.io/)

Format code

    ./gradlew ktlintFormat

Run unit tests

    ./gradlew test

Run test coverage report

    ./gradlew test jacocoTestReport
    firefox build/reports/jacoco/test/html/index.html

Lint, test, run

    ./gradlew ktlintFormat test run

Dev build

    ./gradlew build

Run installed

    ./gradlew installDist
    ./build/install/todo/bin/todo
