FROM openjdk:17-jdk-slim

WORKDIR /app

COPY C:/Users/Maaouia/Desktop/5DS6-Kaddem-main/target/kaddem-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8089

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
