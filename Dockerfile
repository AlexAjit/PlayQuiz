# Use OpenJDK as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the source code into the container
COPY . .

# Build the project using Maven
RUN ./mvnw clean package -DskipTests

# Copy the built JAR file into the container
COPY target/*.jar app.jar

# Expose the port that the app runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]