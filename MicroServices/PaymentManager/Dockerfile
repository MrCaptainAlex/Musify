FROM maven:3-openjdk-17-slim as builder
WORKDIR /project
COPY MicroServices/PaymentManager/ .
RUN mvn package

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=builder /project/target/PaymentManager-0.0.1-SNAPSHOT.jar ./PaymentManager.jar
CMD java -jar PaymentManager.jar