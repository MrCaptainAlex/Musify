FROM maven:3-openjdk-17-slim as builder
WORKDIR /project
COPY api-gateway/ .
RUN mvn package

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=builder /project/target/api-gateway-0.0.1-SNAPSHOT.jar ./api-gateway.jar
CMD java -jar api-gateway.jar

EXPOSE 8080

#FROM openjdk:17-alpine
#ADD ./target/api-gateway.jar /app/
#CMD ["java", "-Xmx200m", "-jar","/app/api-gateway.jar"]

#EXPOSE 8080