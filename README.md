# Daytrader

## Installation

To install Daytrader on an EC2 instance running CentOS 6.4 you will:
1. Install Docker
1. Start the `daytrader-mysql` container
1. Start the `daytrader` container
1. Initialize the mysql database
1. Deploy the app

### Install Docker

Run the following commands as root to install Docker on CentOS 6.

```shell
  $ yum install epel-release
  $ sudo sed -i "s/mirrorlist=https/mirrorlist=http/" /etc/yum.repos.d/epel.repo
  $ yum -y update
  $ sudo yum install docker-io
  $ /etc/init.d/docker start
  $ sudo chkconfig docker on
```

### Start the `daytrader-mysql` container

```shell
  $ docker run --name daytrader-mysql \
               -e MYSQL_ROOT_PASSWORD=HKPoKqdt4LB%4dwJjUVcqVZVx \
               -d jaxzin/daytrader-data:latest
```

### Start the `daytrader` container

```shell
  $ docker run -t -p 80:8080 \
               --name daytrader \
               --link daytrader-mysql:mysql \
               -d jaxzin/daytrader:latest
```

### Initialize the mysql database

```shell
  $ docker -it exec daytrader-mysql /bin/bash
  $ mysql --password=HKPoKqdt4LB%4dwJjUVcqVZVx < Table.ddl
  $ exit
```

### Deploy the app

```shell
  $ docker exec daytrader /apps/deploy.sh
```

You should now be able to access the application on port 80 of your EC2 instance. Be sure to use the config utilities web page to initialize the schema

###

## Developing

1. [Install Docker]
1. Setup the MySQL container by running `src/develop/dev-db.sh`
1. Build and deploy the app by running `src/develop/dev-reset.sh`

