FROM openjdk:8
EXPOSE 8080
ADD target/health-care-docker.jar health-care-docker.jar
ENTRYPOINT ["java", "-jar", "/health-care-docker.jar"]