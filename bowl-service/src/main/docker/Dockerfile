FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD bowl-service-0.0.1.jar bowl-service.jar
RUN sh -c 'touch /bowl-service.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /bowl-service.jar" ]
EXPOSE 8083
LABEL vendor="dmitry"
MAINTAINER Mitya sc <dmitry@starcoders.com>