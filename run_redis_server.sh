#!/bin/sh
set -e
mvn -B --quiet package -DskipTests
exec java -jar ./target/Redis-1.0.jar
