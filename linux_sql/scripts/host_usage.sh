#!/bin/bash

#First lets check if we are following the correct usage
#correct script usage is: bash scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password
if [ "$#" -ne 5 ]; then
	echo "Incorrect usage, please follow: bash scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password" 
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

#save vmstat command to a variable for easy reuse
vmstat_m=$(vmstat --unit M)
vmstat_d=$(vmstat -d)

#now lets parse server CPU and memory usage data
timestamp=$(date '+%Y-%m-%d %H:%M:%S')
hostname=$(hostname -f)
memory_free=$(echo "$vmstat_m" | grep -E --invert-match "procs|r" | awk '{print $4}' | xargs)
cpu_idel=$(echo "$vmstat_m" | grep -E --invert-match "procs|r" | awk '{print $15}' | xargs)
cpu_kernel=$(echo "$vmstat_m" | grep -E --invert-match "procs|r" | awk '{print $14}' | xargs)
disk_io=$(echo "$vmstat_d" | grep -E --invert-match "disk|*total" | awk '{print $10}' | xargs)
disk_available=$(df -BM / | grep -E --invert-match "Filesystem" | awk -F '[ M]*' '{print $4}' | xargs)

#now we need to construct INSERT statement, so lets save it into a variable
insert_stmt=$(cat << EOF
insert into host_usage ("timestamp", host_id, memory_free, cpu_idel, cpu_kernel, disk_io, disk_available)
values ('$timestamp', (SELECT id FROM host_info WHERE hostname = '$hostname'), $memory_free, $cpu_idel, $cpu_kernel, $disk_io, $disk_available)
EOF
)

#finally, we can execute the INSERT statement through psql CLI tool
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"

#exit with success and echo a success statement
echo "Successfully inserted into the psql db that was specified"
exit 0