FROM openjdk:17-jdk

COPY target/picpay-simplificado-0.0.1-SNAPSHOT.jar /app/app.jar

CMD ["java","-jar","/app/app.jar"]