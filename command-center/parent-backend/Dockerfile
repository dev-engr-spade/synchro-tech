FROM openjdk:22-jdk-alpine

LABEL authors="Spade"

WORKDIR /app

COPY target/commandcenter-0.1.0.jar app.jar

EXPOSE:8080

CMD: ["java", "-jar", "app.jar"]
