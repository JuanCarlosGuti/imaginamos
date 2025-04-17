FROM eclipse-temurin:17-jdk-alpine AS api-gateway
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]
