-- we are answering these  questions to manage the cluster better and also plan for future recourses

-- first is to Group hosts by hardware info
--Group hosts by CPU number and sort by their memory size in descending order(within each cpu_number group)

select 
	cpu_number, 
	id as "host_id", 
	total_mem
from 
	host_info
order by 
	cpu_number,
	total_mem desc;

-- next is to get Average memory usage
--Average used memory in percentage over 5 mins interval for each host. (used memory = total memory - free memory).

