# Primeira etapa: compilação e empacotamento do aplicativo
FROM ubuntu:latest AS build
RUN apt-get update
RUN apt-get install openjdk-21-jdk -y
COPY . .
RUN apt-get install maven -y
# Pule os testes na construção do Maven
RUN mvn clean install -DskipTests

# Segunda etapa: criação da imagem mínima
FROM openjdk:21-jdk-slim
EXPOSE 8080
COPY --from=build /target/springSecurityJwt-0.0.1-SNAPSHOT.jar app.jar
CMD ["sh", "-c", "sleep 30 && java -jar app.jar"]
