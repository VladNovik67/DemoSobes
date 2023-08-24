FROM openjdk:19
LABEL authors="Alexei Bakholdin"
ADD /target/spring-boot_security-demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
