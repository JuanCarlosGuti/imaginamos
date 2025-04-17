FROM eclipse-temurin:17-jdk-alpine AS inventory-service
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
