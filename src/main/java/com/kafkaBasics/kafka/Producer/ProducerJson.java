package com.kafkaBasics.kafka.Producer;


import com.kafkaBasics.kafka.Constants;
import com.kafkaBasics.kafka.pojo.Event;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProducerJson {

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "1");
        props.put("retries", 1);
        props.put("batch.size", 1);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "com.taining.kafka.serializer.KafkaJsonSerializer");
        KafkaProducer<String, Event> producer = new KafkaProducer<String,Event>(props);
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
            ProducerRecord<String,Event> record = new ProducerRecord(Constants.topicEvent,list.get(index),event);
            producer.send(record);
        }
        producer.close();
    }
}
