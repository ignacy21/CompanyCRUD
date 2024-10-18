FROM maven:3.9.5 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/target/CompanyCRUD-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "CompanyCRUD-0.0.1-SNAPSHOT.jar"]