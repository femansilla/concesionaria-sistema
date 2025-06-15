# Etapa 1: build con Maven y JDK
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn -pl servicios-mecanicos-service clean package -DskipTests

# Etapa 2: runtime con JRE
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/servicios-mecanicos-service/target/servicios-mecanicos-service-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
