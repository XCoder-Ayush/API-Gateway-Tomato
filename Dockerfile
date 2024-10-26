FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/tomato-api-gateway.jar tomato-api-gateway.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "tomato-api-gateway.jar"]
