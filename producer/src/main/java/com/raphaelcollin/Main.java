package com.raphaelcollin;

import com.raphaelcollin.kafka.User;
import com.raphaelcollin.model.Event;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Slf4j
public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:29092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://127.0.0.1:8081");

        try (Producer<String, User> producer = new KafkaProducer<>(props)) {
            EventGenerator generator = new EventGenerator();
            for (int i = 1; i <= 10; i++) {
                System.out.println("Generating Event #" + i);

                Event event = generator.newRandomEvent();

                String key = extractKey(event);
                User value = extractValue(event);

                ProducerRecord<String, User> record = new ProducerRecord<>("users", key, value);

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

    private static User extractValue(Event event) {
        return new User(event.getUser().getId(), event.getUser().getName(), event.getUser().getEmail());
    }

    private static void sleep(long milliseconds) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}