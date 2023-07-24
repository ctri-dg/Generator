#!/bin/bash
cd ./output/client
yarn install 
yarn build
cd ..
cp -r ./client/dist/* ./server/data-provider/src/main/resources/templates
cp -r ./client/dist/* ./server/data-provider/src/main/resources/static

cd ./output/server/naming-server
mvn clean
mvn package
mv ./target/naming-server-0.0.1-SNAPSHOT.jar ../../buildtarget/naming-server.jar

cd ./server/data-provider
mvn clean
mvn package -DskipTests
mv ./target/data-provider-0.0.1-SNAPSHOT.jar ../../buildtarget/application.jar

cd ../api-gateway
mvn clean
mvn package
mv ./target/api-gateway-0.0.1-SNAPSHOT.jar ../../buildtarget/api-gateway.jar



