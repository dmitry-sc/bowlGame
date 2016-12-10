FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD game-service-0.0.1.jar game-service.jar
RUN sh -c 'touch /game-service.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /game-service.jar" ]
EXPOSE 8082
LABEL vendor="dmitry"
MAINTAINER Mitya sc <dmitry@starcoders.com>