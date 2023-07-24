#!/bin/bash
cd ./output/server/naming-server
mvn clean
mvn spring-boot:build-image -DskipTests

cd ../data-provider
mvn clean
mvn spring-boot:build-image -DskipTests

cd ../api-gateway
mvn clean
mvn spring-boot:build-image -DskipTests




