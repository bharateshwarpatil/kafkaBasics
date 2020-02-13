package com.taining.kafka.consumer;

import com.taining.kafka.Constants;
import com.taining.kafka.pojo.Event;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.Collections;
import java.util.Properties;

public class ConsumerParttition  {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "1");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "com.taining.kafka.serializer.KafkaJsonDeSerializer");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, Constants.MAX_POLL_RECORDS);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, Constants.OFFSET_RESET_EARLIER);
        KafkaConsumer<String, Event> consumer = new KafkaConsumer<String, Event>(props);

        //consumer.seek(new TopicPartition(Constants.topictest,1),0);

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
