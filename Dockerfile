FROM openjdk:21-oracle
WORKDIR /app
COPY build/libs/booking-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080/tcp
CMD ["java", "-jar", "app.jar"]