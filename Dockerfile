FROM openjdk:8-jdk-alpine
COPY target/travels-api-*.jar /app/travels-api.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app/travels-api.jar"]