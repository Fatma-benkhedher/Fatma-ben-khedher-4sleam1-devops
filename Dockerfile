# Use Java 17 base image
FROM openjdk:17-alpine

# Copy your Spring Boot jar
COPY target/*.jar app.jar

# Expose the port your app uses
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "/app.jar"]
