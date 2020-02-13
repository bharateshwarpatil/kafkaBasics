package com.taining.kafka.Producer;

import com.taining.kafka.Constants;
import com.taining.kafka.pojo.Event;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ProducerSync {

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "1");
        props.put("retries", 1);
        props.put("batch.size", 1);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", " com.taining.kafka.serializer.KafkaJsonSerializer");
        //props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"com.taining.kafka.Producer.Partitioner");
        KafkaProducer<String,Event> producer = new KafkaProducer<String,Event>(props);
        for(int i=0 ; i <10;i++){
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
            String typeE=list.get(index);
            event.setType(typeE);
            event.setIndex(i);
            ProducerRecord<String,Event> record = new ProducerRecord(Constants.topicStorm,typeE,event);
            try {
                Future<RecordMetadata> result= producer.send(record);
                RecordMetadata recordMetadataFuture= result.get();//blocking operation
                System.out.println(recordMetadataFuture.offset()+"-"+recordMetadataFuture.partition()+"-"+recordMetadataFuture.topic());
            } catch (InterruptedException e) {
                System.err.println("Interupted thread !!"+e);
            } catch (ExecutionException e) {
                System.err.println("Execution exception"+e);
            }
        }


        producer.close();

    }

}
