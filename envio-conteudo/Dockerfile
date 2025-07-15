FROM maven:3.8.5-eclipse-temurin-17-alpine AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests -q

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.war app.war
RUN rm -rf /tmp/* /var/cache/apk/*
EXPOSE 8084
ENTRYPOINT ["java", "-XX:+UseG1GC", "-XX:+UseCompressedOops", "-jar", "app.war"]