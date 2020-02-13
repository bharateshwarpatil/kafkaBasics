package com.kafkaBasics.kafka.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.kafkaBasics.kafka.pojo.Event;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class KafkaJsonDeSerializer implements Deserializer<Object> {
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public Object deserialize(String topic, byte[] data) {
         if(data!=null){
             ObjectMapper objectMapper = new ObjectMapper();
             try {
               Event event =  objectMapper.readValue(data,Event.class);
               return  event;
             } catch (IOException e) {
                 System.err.println("Error while deserilaise the data");
             }

         }
        return null;
    }

    public void close() {

    }
}
