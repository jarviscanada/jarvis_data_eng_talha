# Hadoop & Hive Project

-	[Introduction](#introduction)
-	[Hadoop Cluster](#hadoop-cluster)
-	[Hive Project](#hive-project)
-	[Improvements](#improvements)

## Introduction

This project's purpose is to process big data and build a big data platform utilizing Apache Hadoop and its components including HDFS, MapReduce, and Yarn. I provisioned a Hadoop Cluster on Google Cloud Platform (that consisted of 1 master node and 2 worker nodes) and then solved business problems using Apache Hive and Zepellin Notebook. The World Development Indicators (WDI) Data on Google BigQuery was the dataset I used after saving a compressed version on Google Cloud Storage. Since this is a very large dataset, the objective is to still be able to easily analyze it and also perform queries on it efficiently. 

## Hadoop Cluster

Below is our cluster architecture diagram:

![HadoopArchitecture](./assets/hadoop_architecture.jpg)

The specifications for all 3 nodes (1 master and 2 workers) is: 2 CPU Cores, 13 GB RAM, and 100 GB of Disk Storage.

As seen above, our master node has all the required services and tools and calls the two worker nodes to store and process the split up dataset. Google Cloud Platform allows us to host and manage this Hadoop Cluster very easily. Below is more info on all the Big Data tools/components used in this project.

-	***Hadoop***: Apache Hadoop is a framework that allows for distributed processing of large data sets across clusters of computers (in our case, VM instances on GCP). It consists of a storage part called the `Hadoop Distributed File System (HDFS)`, a processing part which is a `MapReduce` programming model, and `Yarn` which is a platform responsible for managing computing resources in clusters and using them for scheduling users' applications.

-	***HDFS***: `HSFS` is a distributed file system solution that allows you to store a large number of files across different nodes. It's usage is very similar to Linux and this allows almost any developer to use it without much distributed system knowledge. It has only one NameNode (contains the configuration details and metadeta) and can have multiple Data Nodes which store blocks of data (the master data is split up) on the worker nodes. The Data Nodes execute tasks assigned by the Name Node and then send the results back.

-	***MapReduce***: `MapReduce` is a framework that allows developers to implement algorithms in the `MapReduce` programming model/paradigm for large scale data processing. Facebook developed Hive which is a service that converts SQL queries to `MapReduce`. This allows more developers to use Hadoop without having much distributed system knowledge.

-	***Yarn***: `Yarn` offers the ability to coordinate jobs across different nodes. since the cluster is powerful enough to handle multiple applications at the same time. Hadoop `Yarn` is also a platform responsible for managing computing resources in clusters and using them for scheduling users' applications. The `Yarn Resource Manager` is on the Master Node and keeps track of workers and their hardware usage in order to schedule jobs and allocate resources on the cluster and the `Yarn Node Manager` is on the Worker Nodes. Both the Resource Manager and Node Managers are instances of JVMs.

-	***Hive***: Hive is an SQL like query language (called `HiveQL`) that can process large datasets stored in HDFS. Hive will translate HiveQL to MapReduce jobs, which process data on HDFS (so Hive doesn't store any data files). Hive can also convert SQL to other execution engines, like `Tez` and `Spark`. Hive manages metadata in an RDBMS of your choice and its metadata is very similar to RDBMS DDL like database name, table name, column data type, and HDFS file location (since Hive doesn't store data files). We connect to Hive from Zeppelin through JDBC as shown in the diagram. Another option is to use beeline through the CLI.

-	***Zeppelin***: Zeppelin notebook gives us an easy way to execute and present code in a web notebook. With Zeppelin we can execute Scale, SQL, etc. and also schedule a job (with cron) to run at a regular interval. In our case we are mainly using it to present and manage our code together in order. We had to create a new Hive JDBC interpreter and setup our default drivers, url, and dependencies first in order to get our connection running.

## Hive Project

For our Hive Project we are performing tasks such as: Reading data from google storage, copying data between tables and loading it into HDFS, creating partitions for increasing efficiency, and comparing performance results. 

Below is a screenshot of my Zeppelin Notebook with explanations at each step:

![ZeppelinScreenshot](./assets/zeppelin_screenshot.png)

## Improvements

1. Try adding more worker nodes and see how that affects performance as we only have two worker nodes right now.
2. Test out and create other partition tables for other possible queries on this data.
3. The last two queries compared HQL vs SparkSQL and the results showed that SparkSQL outperfomed HQL both times and was much more efficient. Switching over to SparkSQL should boost our performance moving forward.
