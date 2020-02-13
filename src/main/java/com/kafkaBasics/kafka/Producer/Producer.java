package com.kafkaBasics.kafka.Producer;

import com.kafkaBasics.kafka.Constants;
import kafka.Kafka;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import org.apache.kafka.common.serialization.StringSerializer;
import scala.util.parsing.combinator.testing.Str;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Producer {

    public static void main(String[] args) throws IOException {

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 1);
        props.put("batch.size", 100); //50 m 10
        props.put("linger.ms", 10);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String,String> producer = new KafkaProducer<String,String>(props);
        ProducerRecord<String,String> record = new ProducerRecord(Constants.topic,"group1","hello group1");
        producer.send(record);
        producer.close();

    }
}
