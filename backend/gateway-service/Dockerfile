## Stage 1: Build the application using Gradle 8.8 and JDK 21
#FROM gradle:8.10-jdk21 AS builder
#
## Set the working directory inside the container
#WORKDIR /app
#
## Copy Gradle wrapper and build scripts
#COPY build.gradle settings.gradle /app/
#
## Copy the source code
#COPY src /app/src
#
## Run the Gradle build and skip tests
#RUN gradle clean build --no-daemon -x test
#
## Stage 2: Create the actual runtime container
#FROM openjdk:21-jdk-slim
#
## Set the working directory for the app
#WORKDIR /app
#
## Copy the built JAR file from the builder container
#COPY --from=builder /app/build/libs/gateway-service-0.0.1-SNAPSHOT.jar /app/gateway-service.jar
#
## Expose the port the app will run on
#EXPOSE 8085
#
## Command to run the JAR
#ENTRYPOINT ["java", "-jar", "/app/gateway-service.jar"]


# Dockerfile for Nginx

# Use the official Nginx image as the base
FROM nginx:alpine

# Remove the default Nginx configuration
RUN rm /etc/nginx/conf.d/default.conf

# Copy the custom nginx.conf to the container
COPY nginx.conf /etc/nginx/nginx.conf

# Optional: copy custom error pages if needed
COPY 502.html /usr/share/nginx/html/502.html
