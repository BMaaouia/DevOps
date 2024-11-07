FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/kaddem-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
