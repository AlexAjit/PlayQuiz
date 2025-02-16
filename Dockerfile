# Use OpenJDK 23 as the base image
FROM openjdk:23-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the source code into the container
COPY . .

# Grant execute permissions to the Maven wrapper
RUN chmod +x mvnw

# Build the project using Maven
RUN ./mvnw clean package -DskipTests

# Ensure the target directory exists
RUN mkdir -p target

# Copy the built JAR file into the container
COPY target/*.jar app.jar

# Expose the port that the app runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]