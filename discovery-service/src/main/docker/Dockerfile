FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD discovery-service-0.0.1.jar discovery-service.jar
RUN sh -c 'touch /discovery-service.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /discovery-service.jar" ]
EXPOSE 8761
LABEL vendor="dmitry"
MAINTAINER Mitya sc <dmitry@starcoders.com>