# Stage 1: Build the application using Gradle 8.8 and JDK 21
FROM gradle:8.10-jdk21 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy Gradle wrapper and build scripts
COPY build.gradle settings.gradle /app/

# Copy the source code
COPY src /app/src

# Run the Gradle build and skip tests
RUN gradle clean build --no-daemon -x test

# Stage 2: Create the actual runtime container
FROM openjdk:21-jdk-slim

# Set the working directory for the app
WORKDIR /app

# Copy the built JAR file from the builder container
COPY --from=builder /app/build/libs/eureka-server-0.0.1-SNAPSHOT.jar /app/eureka-server.jar

# Expose the port the app will run on
EXPOSE 8761

# Command to run the JAR
ENTRYPOINT ["java", "-jar", "/app/eureka-server.jar"]
