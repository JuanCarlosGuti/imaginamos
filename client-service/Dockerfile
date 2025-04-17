# Client Service
FROM eclipse-temurin:17-jdk-alpine AS client-service
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "app.jar"]
