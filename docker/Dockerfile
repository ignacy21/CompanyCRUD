# Phaze 1: Building app
FROM maven:3.9.5 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Phaze 2: Starting app
FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/target/CompanyCRUD-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "CompanyCRUD-0.0.1-SNAPSHOT.jar"]
