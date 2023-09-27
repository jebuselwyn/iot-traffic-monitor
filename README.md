# IoT Traffic Monitor

Below is the architecture diagram for IoT Traffic Monitor application. Read the article at [InfoQ](https://www.infoq.com/articles/traffic-data-monitoring-iot-kafka-and-spark-streaming)

![IoT Traffic Monitor Architecture](https://github.com/baghelamit/iot-traffic-monitor/blob/master/iot-architecture.png)

Traffic Monitor application uses following tools and technologies.

- JDK - 1.8
- Maven - 3.3.9
- ZooKeeper - 3.4.8
- Kafka - 2.10-0.10.0.0
- Cassandra - 2.2.6
- Spark - 1.6.2 Pre-built for Hadoop 2.6
- Spring Boot - 1.3.5
- jQuery.js
- Bootstrap.js
- Sockjs.js
- Stomp.js
- Chart.js

IoT Traffic Monitor is a Maven Aggregator project. It includes following three projects.

- IoT Kafka Producer
- IoT Spark Processor
- IoT Spring Boot Dashboard

For building these projects it requires following tools. Please refer README.md files of individual projects for more details.

- JDK - 1.8
- Maven - 3.3.9

Use below command to build all projects.

```sh
mvn package
```



# Install Steps

## Kafka Setup 

### Download and Install Kafka

- Download Kafka from the below url (or latest version)
```wget https://downloads.apache.org/kafka/3.5.1/kafka_2.13-3.5.1.tgz```

- Navigate to the target(install) directory
- Extract the install bundle
```tar -zxvf kafka_2.13-3.5.1.tgz```

### Start the services
- Start zookeeper
```bin/zookeeper-server-start.sh config/zookeeper.properties```
- Start Kafka Broker
```bin/kafka-server-start.sh config/server.properties```

### Setup Kafka Topics

- Create required Topics for IOT Monitor App
```bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic iot-data-event```

- Verify the created Topic
```bin/kafka-topics.sh --list --bootstrap-server localhost:9092```

## Cassandra Setup 

### Download and Install Cassandra

- Download Cassandra from the below url (or latest version)
```wget https://www.apache.org/dyn/closer.lua/cassandra/4.1.3/apache-cassandra-4.1.3-bin.tar.gz```

- Navigate to the target(install) directory

- Extract the install bundle
```tar -zxvf apache-cassandra-4.1.3-bin.tar.gz```

### Start the services

```./cassandra```

### Execute DDLs

```./bin/cqlsh```

```CREATE KEYSPACE IF NOT EXISTS TrafficKeySpace WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};```

```CREATE TABLE TrafficKeySpace.Total_Traffic (routeId text, vehicleType text, totalCount bigint, timeStamp timestamp,recordDate text,PRIMARY KEY (routeId,recordDate,vehicleType));```

```CREATE TABLE TrafficKeySpace.Window_Traffic (routeId text, vehicleType text, totalCount bigint, timeStamp timestamp,recordDate text,PRIMARY KEY (routeId,recordDate,vehicleType));```

```CREATE TABLE TrafficKeySpace.poi_traffic(vehicleid text , vehicletype text , distance bigint,  timeStamp timestamp,PRIMARY KEY (vehicleid));```



## Compile & Package Spring-boot Apps

```mvn package```


### iot-kafka-producer (pre-req: Kafka Setup)

- Start the Spring-boot server (iot-kafka-producer)
```java -jar target/iot-kafka-producer-1.0.0.jar ```


### iot-spark-processor
```spark-submit --class com.iot.app.spark.processor.IoTDataProcessor target/iot-spark-processor-1.0.0.jar```


```
2023-09-23 23:28:22 INFO  IoTDataProcessor:74 - Starting Stream Processing
Exception in thread "main" java.lang.NoSuchMethodError: scala.reflect.api.JavaUniverse.runtimeMirror(Ljava/lang/ClassLoader;)Lscala/reflect/api/JavaMirrors$JavaMirror;
        at com.datastax.spark.connector.util.JavaApiHelper$.mirror(JavaApiHelper.scala:25)
        at com.datastax.spark.connector.util.JavaApiHelper$.getTypeTag(JavaApiHelper.scala:29)
        at com.datastax.spark.connector.util.JavaApiHelper.getTypeTag(JavaApiHelper.scala)
        at com.datastax.spark.connector.japi.CassandraJavaUtil.typeTag(CassandraJavaUtil.java:159)
        at com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow(CassandraJavaUtil.java:1172)
        at com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow(CassandraJavaUtil.java:1205)
        at com.iot.app.spark.processor.IoTTrafficDataProcessor.processTotalTrafficData(IoTTrafficDataProcessor.java:73)
        at com.iot.app.spark.processor.IoTDataProcessor.main(IoTDataProcessor.java:98)



```

Upgrade the libraries (spark/cassandra dependencies) & fix compile error (google.Optional -> spark.Optional)

```
Exception in thread "main" java.lang.NoSuchMethodError: scala.collection.mutable.Set$.empty()Ljava/lang/Object;
        at com.datastax.spark.connector.util.ConfigParameter$.<clinit>(ConfigParameter.scala:64)
        at com.datastax.spark.connector.writer.WriteConf$.<clinit>(WriteConf.scala:63)
        at com.datastax.spark.connector.BatchSize$.<clinit>(BatchSize.scala:14)
        at com.datastax.spark.connector.japi.CassandraJavaUtil.<clinit>(CassandraJavaUtil.java:1664)
        at com.iot.app.spark.processor.IoTTrafficDataProcessor.processTotalTrafficData(IoTTrafficDataProcessor.java:73)
        at com.iot.app.spark.processor.IoTDataProcessor.main(IoTDataProcessor.java:99)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
```

Update to New Kafka Consumer API 


```
Caused by: java.lang.ClassCastException: java.lang.String cannot be cast to com.iot.app.spark.vo.IoTData
        at com.iot.app.spark.processor.IoTDataProcessor.lambda$main$c40bcbb6$1(IoTDataProcessor.java:87)
        at org.apache.spark.api.java.JavaPairRDD$$anonfun$toScalaFunction$1.apply(JavaPairRDD.scala:1040)
        at scala.collection.Iterator$$anon$11.next(Iterator.scala:409)
        at scala.collection.Iterator$$anon$13.hasNext(Iterator.scala:462)
        at scala.collection.Iterator$$anon$11.hasNext(Iterator.scala:408)
        at scala.collection.Iterator$$anon$11.hasNext(Iterator.scala:408)
        at com.datastax.spark.connector.util.CountingIterator.hasNext(CountingIterator.scala:12)
        at com.datastax.spark.connector.writer.GroupingBatchBuilder.hasNext(GroupingBatchBuilder.scala:100)
        at scala.collection.Iterator$class.foreach(Iterator.scala:893)
        at com.datastax.spark.connector.writer.GroupingBatchBuilder.foreach(GroupingBatchBuilder.scala:30)
        at com.datastax.spark.connector.writer.TableWriter$$anonfun$writeInternal$1.apply(TableWriter.scala:229)
        at com.datastax.spark.connector.writer.TableWriter$$anonfun$writeInternal$1.apply(TableWriter.scala:198)
        at com.datastax.spark.connector.cql.CassandraConnector$$anonfun$withSessionDo$1.apply(CassandraConnector.scala:112)
        at com.datastax.spark.connector.cql.CassandraConnector$$anonfun$withSessionDo$1.apply(CassandraConnector.scala:111)
        at com.datastax.spark.connector.cql.CassandraConnector.closeResourceAfterUse(CassandraConnector.scala:129)
        at com.datastax.spark.connector.cql.CassandraConnector.withSessionDo(CassandraConnector.scala:111)
        at com.datastax.spark.connector.writer.TableWriter.writeInternal(TableWriter.scala:198)
        at com.datastax.spark.connector.writer.TableWriter.insert(TableWriter.scala:185)
        at com.datastax.spark.connector.writer.TableWriter.write(TableWriter.scala:172)
        at com.datastax.spark.connector.streaming.DStreamFunctions$$anonfun$sa
```
Fix the Deserializer for IOTData


### iot-springboot-dashboard

java -jar target/iot-springboot-dashboard-1.0.0.jar

```
Caused by: com.datastax.driver.core.exceptions.NoHostAvailableException: All host(s) tried for query failed (tried: /127.0.0.1:9042 (com.datastax.driver.core.exceptions.InvalidQueryException: table schema_keyspaces does not exist))
        at com.datastax.driver.core.ControlConnection.reconnectInternal(ControlConnection.java:240)
        at com.datastax.driver.core.ControlConnection.connect(ControlConnection.java:86)
        at com.datastax.driver.core.Cluster$Manager.init(Cluster.java:1429)
        at com.datastax.driver.core.Cluster.init(Cluster.java:162)
        at com.datastax.driver.core.Cluster.connectAsync(Cluster.java:341)
        at com.datastax.driver.core.Cluster.connect(Cluster.java:286)
        at org.springframework.cassandra.config.CassandraCqlSessionFactoryBean.afterPropertiesSet(CassandraCqlSessionFactoryBean.java:82)
        at org.springframework.data.cassandra.config.CassandraSessionFactoryBean.afterPropertiesSet(CassandraSessionFactoryBean.java:43)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1637)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1574)
        ... 59 more
```

Update the version of spring-boot-starter for cassandra - as the schema has changed post 3.x