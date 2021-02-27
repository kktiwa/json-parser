#!/usr/bin/env bash

set -e

export APP_HOME=/app
export SCALA_LIB="${APP_HOME}/scala/lib/*"
export JAR_FILE="${APP_HOME}/${APP_NAME}-app_2.11-${APP_VERSION}-assembly.jar"

if [[ ! -f "${JAR_FILE}" ]]; then
  echo -e "\x1B[31m App jar does not exist: [${JAR_FILE}] \x1B[0m"
  exit 1
fi

java $JVM_OPTS \
  -cp "${SCALA_LIB}:${JAR_FILE}" au.com.cba.json.api.Server
