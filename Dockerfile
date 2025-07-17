# Use Java 23 base image
FROM eclipse-temurin:23-jdk as builder

# Set working directory
WORKDIR /app

# Copy JAR file
COPY target/travelbookingsystem-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
