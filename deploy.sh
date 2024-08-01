#!/bin/bash

# Build the Gradle project
./gradlew clean build

# Build the Docker image
docker build -t todo-backend:latest --build-arg APP_NAME=todo-backend --no-cache .

# Run Docker Compose to start the service
docker-compose up -d
