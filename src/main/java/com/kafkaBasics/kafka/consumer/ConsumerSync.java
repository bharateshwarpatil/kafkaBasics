package com.kafkaBasics.kafka.consumer;

import com.kafkaBasics.kafka.Constants;
import com.kafkaBasics.kafka.pojo.Event;

import org.apache.kafka.clients.consumer.*;

import java.util.Collections;
import java.util.Properties;

public class ConsumerSync {

    public static void main(String[] args) {



        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "2");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "com.taining.kafka.serializer.KafkaJsonDeSerializer");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, Constants.MAX_POLL_RECORDS);// 100 record then wait for 10 ms and then give
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, Constants.OFFSET_RESET_EARLIER);
        KafkaConsumer<String, Event> consumer = new KafkaConsumer<String, Event>(props);
        consumer.subscribe(Collections.singletonList(Constants.topictest));
        while (true) {
            ConsumerRecords<String, Event> records = consumer.poll(10);
            for (ConsumerRecord<String, Event> record : records) {
                System.out.println(record.key());
                System.out.println(record.value());
                System.out.println(record.partition());
            }
            System.out.println(records.count());
            try {
                consumer.commitSync();
            }catch (CommitFailedException ex){

                System.err.println("Error is not recoverd for commit.  Commit is Failed by consumer !!!"+ex);
            }
        }

    }
}
