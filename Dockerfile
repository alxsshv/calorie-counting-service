FROM eclipse-temurin:21-jre-alpine
ARG JAR_FILE=./build/libs/calorie-service-v.1.jar
WORKDIR /usr/src/app/
COPY ${JAR_FILE} /usr/src/app/calorie-service-v.1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "/usr/src/app/calorie-service-v.1.jar"]