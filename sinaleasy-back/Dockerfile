# Etapa do Build
FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /app
COPY . . 
# Remover o -DskipTests quando criar o ambiente de testes
RUN ./mvnw clean package -DskipTests

# Etapa de Execução
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/transaction-reporter-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]