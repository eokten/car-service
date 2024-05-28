package org.okten.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.okten.demo.dto.event.ProductCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class ProductEventConsumer implements MessageListener<Integer, ProductCreatedEvent> {

    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "car-service-product-listener")
    @Override
    public void onMessage(ConsumerRecord<Integer, ProductCreatedEvent> record) {
        ProductCreatedEvent event = record.value();

        if (Objects.equals(event.getCategory(), "Автотовари")) {
            log.info("Новий продукт для авто: {}", event);
        } else {
            log.info("Новий продукт не є з категорії Автотоварів, тому він нам не потрібен");
        }
    }
}
