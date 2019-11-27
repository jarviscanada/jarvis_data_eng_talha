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

		#set password for default user `postgres`
		export PGPASSWORD=$dbpassword

		#now we run psql
		sudo docker run --rm --name jrvs-psql -e Psql_PASSWORD=$PGPASSWORD -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres

		#finally, we connect to the psql instance uing psql REPL (read–eval–print loop)
		psql -h localhost -U postgres -W
		###Check if we need to do this in the script or not
		;;
	stop)
		#stop docker psql
		sudo docker stop jrvs-psql

		#stop docker, change its status to inactive
		sudo systemctl stop docker
		;;
	#any other case (not start or stop)
	*)
		echo "Incorrect usage, please follow: psql_docker.sh start|stop [db_password]"
		
		#exit with status 1 meaning failure
		exit 1
esac

#now we simply return with success message
exit 0
