package io.conduktor.demos.kafka;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;


public class ConsumerDemo {

    private static final Logger log = LoggerFactory.getLogger(ConsumerDemo.class.getSimpleName());

    public static void main(String[] args) {
        log.info("I'm a Kafka Consumer");

        // setting up properties

        String bootstrapServers = "localhost:9092";
        String group_id = "my_first_application";
        String topic = "java_demo";

        // creating consumer config
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, group_id);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // creating consumer

        KafkaConsumer<String , String> consumer = new KafkaConsumer<String, String>(properties);

        // subscribe consumer to topics

        // to subscribe for only one topic
        //consumer.subscribe(Collections.singletonList(topic));
        // to subscribe to multiple topics
        consumer.subscribe(Arrays.asList(topic));


        // poll for new data
        while(true){
            log.info("polling");
            ConsumerRecords<String , String> records =
                    consumer.poll(Duration.ofMillis(1000));

            for (ConsumerRecord<String , String> record : records) {
                log.info(" Key: " + record.key() + " value: " + record.value() );
                log.info(" partition: " + record.partition() + "offset: " + record.offset() );

            }
        }
    }
}