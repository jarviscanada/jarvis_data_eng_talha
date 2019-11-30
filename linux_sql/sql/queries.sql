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

select 
	host_usage.host_id as host_id, 
	host_info.host_name as host_name, 
	host_info.total_mem as total_memory, 
	avg(
		((host_info.total_mem - host.usage.memory_free) / host_info.total_mem) * 100) 
		as used_memory_percentage 
from 	
	host_usage 
	inner join host_info on host_usage.host_id=host_info.id 
group by
	host_id, 
	host_name, 
	total_memory, 
	DATE_TRUNC('hour', host_usage.timestamp) + DATE_PART('minute', host_usage.timestamp)::int / 5 * interval '5 min' as time 
order by 
	host_usage.host_id;
