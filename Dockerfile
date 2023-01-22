FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar spring-azure-cinema-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Dspring.profiles.active=container","-jar","/spring-azure-cinema-0.0.1-SNAPSHOT.jar"]