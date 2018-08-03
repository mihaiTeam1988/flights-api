FROM quay.io/paulpop/jre-alpine:latest

WORKDIR /

ENV ENVIRONMENT default
ENV ADMIN_PASSWORD admin

COPY build/libs/flights-api-0.0.1-SNAPSHOT.jar /

HEALTHCHECK --interval=5s --retries=10 CMD curl -fs http://localhost:8080/health || exit 1

EXPOSE 8080

CMD java -Djava.security.egd=file:/dev/./urandom -jar -DADMIN_PASSWORD=$ADMIN_PASSWORD \
    ./flights-api-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=$ENVIRONMENT
