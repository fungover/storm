FROM maven:3.8.4-openjdk-17-slim as build
COPY ./ /src
RUN mvn -f /src/pom.xml clean package

FROM eclipse-temurin:17-jre-alpine
COPY --from=build /src/target /app
ENTRYPOINT ["java","-jar","app/storm-jar-with-dependencies.jar"]
