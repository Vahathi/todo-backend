# Use a base image with Gradle and Java 17 for the build stage
FROM gradle:7.3.3-jdk17 AS build
WORKDIR /app

# Copy the Gradle wrapper and build scripts to the container
COPY gradle /app/gradle
COPY gradlew /app/
COPY build.gradle.kts /app/
COPY settings.gradle.kts /app/
RUN chmod +x gradlew

# Download dependencies (this step is separated to optimize caching)
RUN ./gradlew build --no-daemon || return 0

# Copy the rest of the application code
COPY src /app/src

# Build the application
RUN ./gradlew build --no-daemon -x test

# Second stage: create the final lightweight image with OpenJDK 17
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 80
EXPOSE 443

# Run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
