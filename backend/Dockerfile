# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B
# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/flower-billing-system-0.0.1-SNAPSHOT.jar app.jar

# Render requires this port dynamically
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
