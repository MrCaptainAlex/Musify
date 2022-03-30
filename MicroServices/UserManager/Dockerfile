FROM maven:3-openjdk-17-slim as builder
WORKDIR /project
COPY MicroServices/UserManager/ .
RUN mvn package

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=builder /project/target/UserManager-0.0.1-SNAPSHOT.jar ./UserManager.jar
CMD java -jar UserManager.jar