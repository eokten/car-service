package org.okten.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.event.model.ProductCreatedPayload;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Consumer;

@Component("on-product-created")
@Slf4j
public class ProductCreatedConsumer implements Consumer<Message<ProductCreatedPayload>> {
    @Override
    public void accept(Message<ProductCreatedPayload> productCreatedPayloadMessage) {
        ProductCreatedPayload payload = productCreatedPayloadMessage.getPayload();

        if (Objects.equals(payload.getCategory(), "Автотовари")) {
            log.info("Новий продукт для авто: {}", payload);
        } else {
            log.info("Новий продукт не є з категорії Автотоварів, тому він нам не потрібен");
        }
    }
}
