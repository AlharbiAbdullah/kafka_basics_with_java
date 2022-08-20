package io.conduktor.demos.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoWithKeys {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemoWithKeys.class.getSimpleName());

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

        //ProducerRecord<String , String> producerRecord =
               // new ProducerRecord<>("demo_java" , "I'm a producer");

        // creating a for loop to produce more dummy data with a callback
        for( int i=0 ; i<10 ; i++){

            String topic = "demo_java";
            String value = "Hello, Abdullah" + i ;
            String key = "_id" + i;

            ProducerRecord<String , String> producerRecord =
                    new ProducerRecord<>(topic, key , value );

            producer.send(producerRecord, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    // execute everytime is a record successfully sent of throw an exception
                    if (e == null){
                        // the record was successfully sent
                        log.info(
                                "Received new metadata/ \n" +
                                        "Topic: " +metadata.topic() + "\n" +
                                        "key" + producerRecord.key() + "\n" +
                                        "Partition: " + metadata.partition() + "\n" +
                                        "Offset: " + metadata.offset() + "\n" +
                                        "Timestamp: " + metadata.timestamp()
                        );
                    }else {
                        log.error("Error while producing" , e);
                    }
                }
            });
        }
        // send data  - asynchronous operation

        // here I add the callback
        /*
        producer.send(prodcerRec, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception e) {
                        // execute everytime is a record successfully sent of throw an exception
                        if (e == null){
                            // the record was successfully sent
                            log.info(
                                    "Received new metadata/ \n" +
                                            "Topic: " +metadata.topic() + "\n" +
                                            "Partition: " + metadata.partition() + "\n" +
                                            "Offset: " + metadata.offset() + "\n" +
                                            "Timestamp: " + metadata.timestamp()
                            );
                        }else {
                            log.error("Error while producing" , e);
                        }
                    }
                });

         */


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
