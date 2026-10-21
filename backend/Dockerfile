# Step 1: Build the JAR using Maven
FROM maven:3.10-eclipse-temurin-25 AS build
WORKDIR /app
COPY . .
RUN mvn package -DskipTests

# Step 2: Run the JAR using a small Java runtime image
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
