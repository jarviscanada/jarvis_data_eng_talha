
# Spark/Scala Project

-	[Introduction](#introduction)
-	[Spark Architecture](#spark-architecture)
-	[Spark RDD Project](#spark-rdd-project)
-	[Spark Dataframe Project](#spark-dataframe-project)

## Introduction

The purpose of this project was to continue working with and processing big data, but this time using Scala and Apache Spark. In this project, I worked on various business problems with different datasets using Spark Resilient Distributed Datasets (or RDD) and Structured APIs. I will go further into those in the following sections. I also used Zeppelin Notebook which gives us an easy way to execute and present our Scala and SparkSQL code in a web notebook.


## Spark Architecture

Below is our Spark application architecture diagram:

![SparkArchitecture](./assets/spark_architecture.jpg)

-	***Drivers and Executors***: Driver process/JVM can run on client machine (client mode) or one of the cluster node (cluster mode). All executors (JVMs) are distributed across cluster nodes and managed by YARN.

-	***Yarn***: `Yarn` offers the ability to coordinate jobs across different nodes since the cluster is powerful enough to handle multiple applications at the same time. Hadoop `Yarn` is also a platform responsible for managing computing resources in clusters and using them for scheduling users' applications. The `Yarn Resource Manager` is on the Master Node and keeps track of workers and their hardware usage in order to schedule jobs and allocate resources on the cluster and the `Yarn Node Manager` is on the Worker Nodes. Both the Resource Manager and Node Managers are instances of JVMs.

-	***Spark Context***: SparkContext is the entry gate of Apache Spark functionality. The most important step of any Spark driver application is to generate SparkContext. It allows your Spark Application to access the Spark Cluster with the help of the Yarn Resource Manager. SparkSession was introduced in Spark 2.0 to make it easy for developers so we don't have to worry about different contexts and to streamline the access to different contexts. By having SparkSession, we automatically have access to the SparkContext.

## Spark RDD Project

An RDD is a resilient and distributed collection of records spread over one or many partitions. It is Resilient meaning that it is fault-tolerant with the help of RDD lineage graph and therefore it is able to recompute missing or damaged partitions due to node failures. It is distributed with data residing on multiple nodes in a cluster. The dataset is a collection of partitioned data with primitive values or values of values, e.g. tuples or other objects (that represent records of the data you work with).

RDDs support two kinds of operations: 
- `transformations`, which are lazy operations that return another RDD.
-   `actions`, which are operations that trigger computation and return values.

For this project I created an RDD from a text file, fixed its formatting issue, performed RDD Transformations and Actions, and then ran some queries.

Below is is a screenshot of my Zeppelin Notebook with explanations at each step:

![SparkRDDProject](./assets/spark_rdd_project.png)


## Spark Dataframe Project


The Structured APIs are a tool for manipulating all sorts of data, from unstructured log files to semi-structured CSV files and highly structured Parquet files. These APIs refer to three core types of distributed collection APIs:

- Datasets
- DataFrames
- SQL tables and views

For this project I created a Dataframe from a text file stored on hdfs and then ran some Dataframe Operations and used SQL to query the Dataframe. Also at the end I graphed some query results as well.

Below is is a screenshot of my Zeppelin Notebook with explanations at each step:

![SparkDataframeProject](./assets/spark_dataframe_project.png)
