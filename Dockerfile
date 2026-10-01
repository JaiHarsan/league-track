# Stage 1: Build stage using Maven and JDK 17
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Package the Spring Boot application (skipping tests for faster build)
RUN mvn clean package -DskipTests

# Stage 2: Runtime stage with lightweight JRE 17
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built jar file from stage 1
COPY --from=build /app/target/league-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 (Render dynamically maps $PORT env var)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
