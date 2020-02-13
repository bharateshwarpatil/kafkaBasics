package com.taining.kafka.Producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.cluster.Partition;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class Partitioner implements org.apache.kafka.clients.producer.Partitioner {


    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {

      System.out.println(String.format("key %s",key));
      String keyStr = String.valueOf(key);
       int numberOFPartition = 4;//cluster.partitionCountForTopic("events");

      if("SystemAlerts".equals(keyStr)){
          return  numberOFPartition;
      }
          return (keyStr.hashCode() % (numberOFPartition-1));
    }

    public void close() {

    }

    public void configure(Map<String, ?> configs) {

    }
}
