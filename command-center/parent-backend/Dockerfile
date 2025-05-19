# Use a base image with Java
FROM openjdk:21-jdk-slim AS build

LABEL authors="Spade"

# Set the working directory
WORKDIR /app

# Copy the Maven build file
COPY command-center/parent-backend/pom.xml .

# Copy the source code
COPY command-center/parent-backend/src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Use a smaller base image for the final image
FROM openjdk:22-jre-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port (default Spring Boot port)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
