#!/bin/bash

#This is how to use the script:
#./scripts/psql_docker.sh start|stop [db_password]

command=$1
dbpassword=$2

case $command in
	start)
		#first we start docker
		sudo systemctl status docker || sudo systemctl start docker

		#then get psql docker image
		sudo docker pull postgres

		#set password for default user `postgres` by setting this environment variable
		export PGPASSWORD=$dbpassword

		#now we run psql
		sudo docker run --rm --name jrvs-psql -e Psql_PASSWORD=$PGPASSWORD -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres

		#Here, --rm will remove the container when it exits, we specify that the container is named jrvs-psql
		#	   -e is setting environment variable, -v is mounting that volume

		#we can connect to the psql instance uing psql REPL (read–eval–print loop)
		#psql -h localhost -U postgres -W
		;;
	stop)
		#stop psql docker container
		sudo docker stop jrvs-psql
		;;
	#any other case (not start or stop)
	*)
		echo "Incorrect usage, please follow: psql_docker.sh start|stop [db_password]"
		
		#exit with status 1 meaning failure
		exit 1
esac

#now we simply return with success message
exit 0
