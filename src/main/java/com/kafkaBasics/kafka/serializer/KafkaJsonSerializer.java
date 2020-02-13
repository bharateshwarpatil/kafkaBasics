package com.taining.kafka.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taining.kafka.pojo.Event;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class KafkaJsonSerializer implements Serializer<Event> {

    public void configure(Map configs, boolean isKey) {

    }

    public byte[] serialize(String topic, Event data) {
        if(data!=null){
            byte[] retVal = null;
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String sdata=objectMapper.writeValueAsString(data);
                System.out.println(sdata);
                retVal = sdata.getBytes();
              } catch (JsonProcessingException e) {
                throw  new SerializationException("Not able to serialize the object",e);
            }

            return retVal;
        }
        return new byte[0];
    }

    public void close() {

    }
}
