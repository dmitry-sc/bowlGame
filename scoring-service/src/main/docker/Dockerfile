FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD scoring-service-0.0.1.jar scoring-service.jar
RUN sh -c 'touch /scoring-service.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /scoring-service.jar" ]
EXPOSE 8081
LABEL vendor="dmitry"
MAINTAINER Mitya sc <dmitry@starcoders.com>