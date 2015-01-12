#!/bin/bash
# A utility script for building the project and
#   redeploying the app in a local instance of docker
#
# Prereq:
#  a docker container named daytrader-mysql running the 'jaxzin/daytrader-data:3.0.0' image

mvn clean install
docker build -t="jaxzin/daytrader:latest" .
docker stop daytrader
docker rm daytrader
docker run -t -p 8080:8080 -p 1099:1099 --name daytrader --link daytrader-mysql:mysql -d jaxzin/daytrader:latest

echo "Waiting for Geronimo to start..."
sleep 5
echo "Deploying daytrader to Geronimo..."
docker exec daytrader /apps/deploy.sh
