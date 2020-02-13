package com.kafkaBasics.kafka.Producer;

import com.kafkaBasics.kafka.Constants;
import com.kafkaBasics.kafka.pojo.Event;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerAsync {

    private static class DemoProducerCallback implements Callback {

        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            System.out.println(recordMetadata.offset()+"-"+recordMetadata.partition()+"-"+recordMetadata.topic());

            if (e != null) {
                e.printStackTrace();
            }
        } }
    public static void main(String[] args) throws IOException {
        KafkaProducer<String, Event> producer=null;
     try {
         Properties props = new Properties();
         props.put("bootstrap.servers", "localhost:9092");
         props.put("acks", "1");
         props.put("retries", 1);
         props.put("batch.size", 1);
         props.put("linger.ms", 1);
         props.put("buffer.memory", 33554432);
         props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
         props.put("value.serializer", " com.taining.kafka.serializer.KafkaJsonSerializer");
         producer = new KafkaProducer<String, Event>(props);
         for(int i=0 ; i <100000 ;i++){
             //alert, SystemAlert, warning, Simple event
             List<String> list = new ArrayList<String>();
             list.add("AppAlerts");
             list.add("SystemAlerts");
             list.add("ApplicationEvent");
             list.add("SystemEvent");
             list.add("os");
             list.add("App");
             Event event =   new Event("cpu is high for last 10 mins on host x","system_alert");
             int index=i%(list.size()-1);
             event.setType(list.get(index));
             event.setIndex(i);
             ProducerRecord<String,Event> record = new ProducerRecord(Constants.topicEvent,list.get(index),event);
             producer.send(record,new DemoProducerCallback());
          }
         System.out.println("executing next steps !!");
      }finally {
         producer.close();
      }


    }


}
