FROM maven:3.6.3-openjdk-11-slim AS builder

WORKDIR /build
COPY pom.xml .

RUN mvn dependency:go-offline

COPY . .

RUN mvn -B clean package -DskipTests

FROM openjdk:11-slim

WORKDIR /app
COPY --from=builder /build/target/*.jar /app/netbank.jar

ENV SERVER_PORT=8080

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "netbank.jar"]
