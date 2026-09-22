# Etapa 1: compilar el jar con Maven y Java 21.
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build

# Las dependencias se descargan en una capa aparte: mientras el pom no cambie,
# Docker reutiliza la cache y la imagen se reconstruye en segundos.
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

# Etapa 2: solo el runtime, sin Maven ni codigo fuente.
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /build/target/branch-engine-1.0.0.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
