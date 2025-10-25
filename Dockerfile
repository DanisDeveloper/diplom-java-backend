FROM gradle:8.7-jdk21 AS builder
WORKDIR /app

COPY gradlew .
COPY gradle gradle

RUN ./gradlew build -x test --no-daemon || true

COPY build.gradle.kts .
COPY settings.gradle.kts .

COPY src src

RUN ./gradlew bootJar --no-daemon

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar
#COPY ./build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]