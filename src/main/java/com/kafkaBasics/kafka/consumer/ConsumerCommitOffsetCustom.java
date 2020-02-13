package com.kafkaBasics.kafka.consumer;


import com.kafkaBasics.kafka.Constants;
import com.kafkaBasics.kafka.pojo.Event;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConsumerCommitOffsetCustom {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "3");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "com.taining.kafka.serializer.KafkaJsonDeSerializer");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, Constants.MAX_POLL_RECORDS);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, Constants.OFFSET_RESET_EARLIER);
        KafkaConsumer<String, Event> consumer = new KafkaConsumer<String, Event>(props);
        consumer.subscribe(Collections.singletonList(Constants.topicJSON));
         Map<TopicPartition, OffsetAndMetadata> currentOffsets =new HashMap<TopicPartition, OffsetAndMetadata>();
        while (true) {
            ConsumerRecords<String, Event> records = consumer.poll(10);
            int i=0;
            for (ConsumerRecord<String, Event> record : records) {
                System.out.println(record.key());
                System.out.println(record.value());
                System.out.println(record.partition());
                currentOffsets.put(new TopicPartition(record.topic(),record.partition()),new OffsetAndMetadata(record.offset()+1));
               if(i%10==0){
                   System.out.println("commit ");
                   consumer.commitAsync(currentOffsets,null);
               }
                i++;
            }
            System.out.println(records.count());
        }
    }

}
