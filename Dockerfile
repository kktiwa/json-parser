ARG DOCKER_UPSTREAM_REGISTRY

ARG APP_NAME
ARG APP_VERSION
ARG SCALA_VERSION=2.11.12

ENV APP_NAME=${APP_NAME}
ENV APP_VERSION=${APP_VERSION}

FROM ${DOCKER_UPSTREAM_REGISTRY}/cp-base-new:${DOCKER_UPSTREAM_TAG}

USER root

WORKDIR /app

RUN \
    curl -Lo scala-${SCALA_VERSION}.tgz https://www.scala-lang.org/files/archive/scala-${SCALA_VERSION}.tgz && \
    tar zxvf scala-${SCALA_VERSION}.tgz && \
    mv scala-${SCALA_VERSION} scala && \
    rm scala-${SCALA_VERSION}.tgz

COPY docker-entrypoint.sh .
RUN chmod +x docker-entrypoint.sh

#RUN mkdir -p /opt/lib
COPY target/scala-2.11/zip-coding-challenge-assembly-0.1.0-SNAPSHOT.jar .

EXPOSE 9000
ENTRYPOINT ["./docker-entrypoint.sh"]
