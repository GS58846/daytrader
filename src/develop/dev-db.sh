#!/bin/bash
# A utility script for building the project database and
#   redeploying the database in a local instance of docker


docker build -t="jaxzin/daytrader-data:latest" bin/dbscripts/mysql
docker stop daytrader-mysql
docker rm daytrader-mysql
docker run --name daytrader-mysql -e MYSQL_ROOT_PASSWORD=HKPoKqdt4LB%4dwJjUVcqVZVx -d jaxzin/daytrader-data:latest
docker exec daytrader-mysql mysql --password=HKPoKqdt4LB%4dwJjUVcqVZVx < Table.ddl