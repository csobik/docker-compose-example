FROM frolvlad/alpine-oraclejdk8:slim

MAINTAINER Csobik <csobik@gmail.com>

ADD docker-compose-example-executable.jar app.jar

RUN sh -c 'touch /app.jar'

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]