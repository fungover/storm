FROM maven:3.8.4-openjdk-17-slim as build
COPY ./ /src
RUN mvn -f /src/pom.xml clean package
RUN jlink --module-path /src/target/storm.jar --add-modules org.fungover.storm --output /src/target/standalone

FROM eclipse-temurin:17-jre-alpine
COPY --from=build /src/target/standalone /app
ENTRYPOINT ["/app/bin/java","--module","org.fungover.storm/org.fungover.storm.Server"]