#!/bin/bash

#First lets check if we are following the correct usage
#correct script usage is: ./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
if [ "$#" -ne 5 ]; then
	echo "Incorrect usage, please follow: ./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password" 
	exit 1
fi

#First we assign arguements to variables
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#set PGPASSWORD environment variable to the entered password, helps us avoid password prompt later
export PGPASSWORD=$psql_password

#save lscpu command to a variable for easy reuse
lscpu_out=`lscpu`

#now lets parse host hardware specifications using bash cmds
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out"  | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out"  | egrep "^Model name:" | awk '{print $3, $4, $5, $6, $7}' | xargs)
cpu_mhz=$(echo "$lscpu_out"  | egrep "^CPU MHz:" | awk '{print $3}' | xargs)
L2_cache=$(echo "$lscpu_out"  | egrep "^L2 cache:" | awk -F '[ K]*' '{print $3}' | xargs)
total_mem=$(cat /proc/meminfo | grep "^MemTotal" | awk '{print $2}' | xargs)
timestamp=$(date '+%Y-%m-%d %H:%M:%S')

#now we need to construct INSERT statement, so lets save it into a variable
insert_stmt=$(cat << EOF
insert into host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, "timestamp")
values ('$hostname', '$cpu_number', '$cpu_architecture', '$cpu_model', '$cpu_mhz', '$L2_cache', '$total_mem', '$timestamp')
EOF
)

#finally, we can execute the INSERT statement through psql CLI tool
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"

#exit with success and echo a success statement
echo "Successfully inserted into the psql db that was specified"
exit 0