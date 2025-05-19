## Use Amazon Corretto 21 as the base image
#FROM amazoncorretto:21 AS build
#
## Set the working directory
#WORKDIR /app
#
## Copy the Maven build file
#COPY command-center/parent-backend/pom.xml .
#
## Copy the source code
#COPY command-center/parent-backend/src ./src
#
## Build the application
#RUN ./mvnw clean package -DskipTests
#
## Use a smaller base image for the final image
#FROM amazoncorretto:21
#
## Set the working directory
#WORKDIR /app
#
## Copy the built JAR file from the build stage
#COPY --from=build /app/target/*.jar app.jar
#
## Expose the application port (default Spring Boot port)
#EXPOSE 8080
#
## Command to run the application
#ENTRYPOINT ["java", "-jar", "app.jar"]


FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /workspace/app

COPY command-center/parent-backend/mvnw .
COPY command-center/parent-backend/.mvn .mvn
COPY command-center/parent-backend/pom.xml .
COPY command-center/parent-backend/src src

RUN chmod +x mvnw
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp

ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","com.synchrotech.commandcenter.CommandCenterApplication"]