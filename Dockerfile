# Use a base image with Gradle and Java 17
FROM gradle:7.3.3-jdk17 AS build
WORKDIR /app

# Copy the Gradle wrapper and build scripts to the container
COPY gradle /app/gradle
COPY gradlew /app/
COPY build.gradle /app/
COPY settings.gradle /app/

# Download dependencies (this step is separated to optimize caching)
RUN ./gradlew build --no-daemon || return 0

# Copy the rest of the application code
COPY src /app/src

# Build the application
RUN ./gradlew build --no-daemon -x test

# Second stage: create the final lightweight image
FROM my-java17-arm:latest
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

# Run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
