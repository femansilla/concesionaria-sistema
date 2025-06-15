# Etapa 1: build con Maven y JDK
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
# le indica a Maven que también construya los módulos requeridos por dependencias (como el parent)
RUN mvn -pl eureka-server -am clean package -DskipTests 

# Etapa 2: runtime con JRE
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/eureka-server/target/eureka-server-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
