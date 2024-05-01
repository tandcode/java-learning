FROM docker.io/amazoncorretto:21-alpine as builder
COPY . .
RUN ./mvnw clean package

FROM docker.io/amazoncorretto:21-alpine
COPY --from=builder target/*jar-with-dependencies.jar /app.jar
ENTRYPOINT ["/entrypoint.sh"]