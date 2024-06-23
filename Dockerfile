# Use a base image with Maven and Java 17
FROM maven:3.8.1-openjdk-17-slim AS build
WORKDIR /app

# Copy the pom.xml file to the container
COPY pom.xml .
# Download dependencies (this step is separated to optimize caching)
RUN mvn dependency:go-offline

# Copy the rest of the application code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Second stage: create the final lightweight image
FROM my-java17-arm:latest
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
