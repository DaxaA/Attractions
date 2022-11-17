FROM openjdk:16
COPY build/libs/*.jar attractions.jar
ENTRYPOINT ["java", "-jar", "attractions.jar"]
EXPOSE 8081