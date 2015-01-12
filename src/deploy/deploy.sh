#!/bin/bash

# Switch in the latest
sed -i 's/$MYSQL_PORT_3306_TCP_ADDR/'$MYSQL_PORT_3306_TCP_ADDR'/g' /apps/daytrader-mysql-xa-plan.xml

/apps/geronimo-tomcat7-javaee6-web-3.0.1/bin/deploy -u system -p manager deploy /apps/daytrader-ear-3.0.0.ear /apps/daytrader-mysql-xa-plan.xml