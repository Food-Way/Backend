FROM openjdk:17
WORKDIR /app
COPY API/target/api-0.0.1-SNAPSHOT.jar /app/api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","api-0.0.1-SNAPSHOT.jar"]
