#!/bin/bash

cd ./output/server/naming-server
mvn clean
mvn package
mv ./target/naming-server-0.0.1-SNAPSHOT.jar ../../buildtarget/naming-server.jar

cd ../data-provider
mvn clean
mvn package
mv ./target/data-provider-0.0.1-SNAPSHOT.jar ../../buildtarget/data-provider.jar

cd ../api-gateway
mvn clean
mvn package
mv ./target/api-gateway-0.0.1-SNAPSHOT.jar ../../buildtarget/api-gateway.jar



