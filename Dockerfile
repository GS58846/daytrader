# Daytrader example installed in Apache Geronimo
FROM jaxzin/daytrader-mysql:3.0.0

MAINTAINER brian@jaxzin.com

ADD javaee6/assemblies/daytrader-ear/target/daytrader-ear-3.0.0.ear /apps/
ADD javaee6/plans/target/classes/daytrader-mysql-xa-plan.xml /apps/

ADD src/deploy/deploy.sh /apps/
RUN chmod a+x /apps/deploy.sh

# Download MySQL JDBC driver
#ADD http://repo1.maven.org/maven2/mysql/mysql-connector-java/5.1.34/mysql-connector-java-5.1.34.jar /apps/

# =====
# These commands need to be run after the container is up,
# so they will be done manually and captures as new Docker images.
# =====
# Install the MySQL driver into Geronimo
#RUN ./deploy --user=system --password=manager install-library --groupId mysql /apps/mysql-connector-java-5.1.34.jar

# Install the app into Geronimo
#RUN ./deploy --user=system --password=manager deploy /apps/daytrader-ear-3.0.0.ear /apps/daytrader-mysql-xa-plan.xml