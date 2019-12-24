### Build backend
FROM gradle:6.0.1-jdk11 AS backend
COPY settings.gradle.kts build.gradle.kts .editorconfig ./
COPY src src/
RUN gradle build installDist

### Release
FROM openjdk:11.0.5-jre-slim-buster AS release
COPY --from=backend /home/gradle/build/install/ ./

CMD [ "./todo/bin/todo" ]
