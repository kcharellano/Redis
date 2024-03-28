#!/bin/sh
set -e
mvn -B --quiet package
exec java -jar ./target/Redis-1.0.jar
