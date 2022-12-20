package com.raphaelcollin;

import com.raphaelcollin.model.Event;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            EventGenerator generator = new EventGenerator();
            for (int i = 1; i <= 10; i++) {
                System.out.println("Generating Event #" + i);

                Event event = generator.newRandomEvent();

                String key = extractKey(event);
                String value = extractValue(event);

                ProducerRecord<String, String> record = new ProducerRecord<>("users", key, value);

                System.out.println("Producing to Kafka the record: " + key + ": " + value);

                producer.send(record).get();

                sleep(1000);
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String extractKey(Event event) {
        return String.format("%d", event.getUser().getId());
    }

    private static String extractValue(Event event) {
        return String.format("%d,%s,%s", event.getUser().getId(), event.getUser().getName(), event.getUser().getEmail());
    }

    private static void sleep(long milliseconds) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}