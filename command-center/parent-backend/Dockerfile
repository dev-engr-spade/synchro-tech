# Use Amazon Corretto 21 as the base image
FROM amazoncorretto:21 AS build

# Set the working directory
WORKDIR /app

# Copy the Maven build file
COPY command-center/parent-backend/pom.xml .

# Copy the source code
COPY command-center/parent-backend/src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use a smaller base image for the final image
FROM amazoncorretto:21

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port (default Spring Boot port)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]