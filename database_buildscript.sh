#!/bin/bash

echo -n "Provide the path to the data volumne : "
read $datavolume

docker network create maintenance
docker stop db
docker rm db
docker run --name db --network maintenance -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=crudapp -d -v $datavolume:/var/lib/mysql mysql