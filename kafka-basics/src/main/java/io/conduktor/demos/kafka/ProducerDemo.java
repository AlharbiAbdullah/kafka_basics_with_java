package io.conduktor.demos.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemo {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());

    public static void main(String[] args) {
        log.info("I'm a Kafka producer");

        // create a producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG , StringSerializer.class.getName());

        // create a producer

        KafkaProducer<String , String> producer  = new KafkaProducer< String , String>(properties);

        // create a producer record

        ProducerRecord<String , String> producerRecord =
                new ProducerRecord<>("abdullah_topic" , "Hello , world!!");

        // send data  - asynchronous operation

        producer.send(producerRecord);

        // Note:
            // when we send data (asynchronous operation) that mean we send data to the destination;
            // but it might not send because it will jump to the next line of code before sending
            // because of that we need to synchronous the data using flush(), that make sure
            // the data is sent.

        // flush data - synchronous operation (it wait until all the data from the producer is sent)
        producer.flush();

        // close producer
        producer.close();

    }
}
