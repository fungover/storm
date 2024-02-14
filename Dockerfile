FROM maven:3.9.6-eclipse-temurin-21 as build
COPY ./ /src
RUN mvn -f /src/pom.xml clean package

FROM eclipse-temurin:21.0.2_13-jre-alpine
COPY --from=build /src/target/modules /app/modules
COPY --from=build /src/target/storm.jar /app/storm.jar
COPY --from=build /src/webroot /webroot
COPY --from=build /src/config/config.json /etc/storm/config/config.json
EXPOSE 8080
ENTRYPOINT ["java","--module-path","app/storm.jar:app/modules","-m","org.fungover.storm/org.fungover.storm.server.Server"]
