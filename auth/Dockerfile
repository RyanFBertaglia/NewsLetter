# Stage 1: Build stage
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine

ENV JAVA_OPTS="-XX:+UseG1GC -XX:+UseCompressedOops -XX:+ExitOnOutOfMemoryError -Djava.security.egd=file:/dev/./urandom"

WORKDIR /app
COPY --from=builder /app/target/*.war ./app.war

RUN addgroup -S spring && adduser -S spring -G spring \
    && chown spring:spring /app/app.war
USER spring

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app/app.war"]