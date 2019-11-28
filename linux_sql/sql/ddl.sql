-- This script creates a database and two tables automatically
-- Use this command to execute: psql -h localhost -U postgres -W -f sql/ddl.sql

--first we drop the database if it already exists
drop database if exists host_agent;

--then we create a new database
create database host_agent;

--now we switch to this database
\connect host_agent

--now we create our first table to store hardware specifications
CREATE TABLE PUBLIC.host_info 
	( 
		id SERIAL NOT NULL PRIMARY KEY, 
		hostname VARCHAR NOT NULL UNIQUE, 
		cpu_number INTEGER NOT NULL,
		cpu_architecture VARCHAR NOT NULL,		
		cpu_model VARCHAR NOT NULL,
		cpu_mhz FLOAT NOT NULL,
		L2_cache INTEGER NOT NULL, 
		total_mem INTEGER NOT NULL,
		"timestamp" TIMESTAMP NOT NULL
		 
		-- id is primary key constraint
		-- unique hostname constraint
	);

--now we create our second table to store resource usage data
CREATE TABLE PUBLIC.host_usage
	( 
		"timestamp" TIMESTAMP NOT NULL, 
		host_id SERIAL NOT NULL, 		
		memory_free INTEGER NOT NULL,
		cpu_idel INTEGER NOT NULL,
		cpu_kernel INTEGER NOT NULL,
		disk_io INTEGER NOT NULL,
		disk_available INTEGER NOT NULL,

		-- add foreign key constraint
		constraint host_fk
		foreign key (host_id) 
		references host_info(id)
	);
