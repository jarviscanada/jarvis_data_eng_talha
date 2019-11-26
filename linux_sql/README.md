
# Linux Cluster Monitoring Agent

## Architecture and Design
Here is an architectural diagram that shows a high level overview of this Project:
![architecture diagram](assets/architecture_diagram.png)

A psql instance that is stored on Server 1 is used to persist all the required data. 
The bash agent on each server gather and insert that data into the psql instance. 
Below is information on each file in the file directory (bash scripts and sql queries)

### Script Descriptions
- host_info.sh
- This script only runs once at installation and is used to collect the host hardware information and then inserts it into the psql database.
- host_usage.sh
- This script collects host usage info (such as the CPU and memory) and then inserts it the psql database. It is triggered by the "crontab" job and runs every minute.
- psql_docker.sh
- This script is used to start and stop the psql database with docker.

### SQL Query Descriptions
- ddl.sql
- This script automates the psql database initialization.
- queries.sql
- This is a set of SQL queries that are used to answer user stories.
