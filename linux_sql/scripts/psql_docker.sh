#!/bin/bash

#This is how to use the script:
#./scripts/psql_docker.sh start|stop [db_password]

command=$1
dbpassword=$2

case $command in
	start)
		#first check if command is in correct format (with password)
		if [ "$#" -ne 2 ]
		then
			echo "Incorrect usage, please follow: psql_docker.sh start|stop [db_password]"
			exit 1
		fi

		#then  we start docker if the docker server is not running
		sudo systemctl status docker || sudo systemctl start docker

		#next we check to see if our container is already running, if it is then simply exit
		if [ `sudo docker ps -f name=jrvs-psql | wc -l` -eq 2 ]
		then
			echo "jrvs-psql container is already running"
			exit 0
		fi

		#then get psql docker image
		sudo docker pull postgres

		#now we check if our volume was already created or not, if not then create it
		if [ `sudo docker volume ls | egrep "pgdata" | awk '{print $2}'` -ne "pgdata" ]
        then
        	sudo docker volume create pgdata
        fi

		#set password for default user `postgres` by setting this environment variable
		export PGPASSWORD=$dbpassword

		#now we check if container is created or not, if not then we create and run it
		#if only one line is returned then that means this container has not been created
		if [ `docker container ls -a -f name=jrvs-psql` -eq 1 ]
        then
        	sudo docker run --name jrvs-psql -e POSTGRES_PASSWORD=$PGPASSWORD -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
        	echo "jrvs-psql container has been created"
        fi

		#now finally we run psql (since it was already created but is not running)
		sudo docker container start jrvs-psql
		echo "jrvs-psql container is now running"


		#we can connect to the psql instance uing psql REPL (read–eval–print loop)
		#psql -h localhost -U postgres -W
		;;
	stop)
		#first check if command is in correct format (nothing after stop)
		if [ "$#" -ne 1 ]
		then
			echo "Incorrect usage, please follow: psql_docker.sh start|stop [db_password]"
			exit 1
		fi

		#check if it is running first or not
		if [ `sudo docker ps -f name=jrvs-psql | wc -l` -eq 1 ]
		then
			echo "the instance has already been stopped"
			exit 1
		else
			#stop psql docker container that is running
			sudo docker container start jrvs-psql
		fi
		;;
	#any other case (not start or stop)
	*)
		echo "Incorrect usage, please follow: psql_docker.sh start|stop [db_password]"
		
		#exit with status 1 meaning failure
		exit 1
esac

#now we simply return with success message
exit 0
