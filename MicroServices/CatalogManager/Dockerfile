FROM maven:3-openjdk-17-slim as builder
WORKDIR /project
COPY MicroServices/CatalogManager/ .
RUN mvn package

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=builder /project/target/CatalogManager-0.0.1-SNAPSHOT.jar ./CatalogManager.jar
CMD java -jar CatalogManager.jar