#!/usr/bin/env bash
#
# Build project
#
MAVEN_OPTS="-Xmx256m"
MAVEN_OPTS="${MAVEN_OPTS} -Dorg.slf4j.simpleLogger.dateTimeFormat=HH:mm:ss,SSS"
MAVEN_OPTS="${MAVEN_OPTS} -Dorg.slf4j.simpleLogger.showDateTime=true"
mvn -P qulice clean install