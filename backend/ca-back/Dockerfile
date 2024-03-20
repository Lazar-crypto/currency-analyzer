# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17-slim AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn package -DskipTests

# Stage 2: Create the final runtime image
FROM openjdk:17-slim
ARG JAR_FILE=/app/target/*.jar
COPY --from=builder $JAR_FILE app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
