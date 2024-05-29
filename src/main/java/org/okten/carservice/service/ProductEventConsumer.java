package org.okten.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.event.api.IProductCreatedConsumerService;
import org.example.event.model.ProductCreatedPayload;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class ProductEventConsumer implements IProductCreatedConsumerService {

    @Override
    public void productCreated(ProductCreatedPayload payload, ProductCreatedPayloadHeaders headers) {
        if (Objects.equals(payload.getCategory(), "Автотовари")) {
            log.info("Новий продукт для авто: {}", payload);
        } else {
            log.info("Новий продукт не є з категорії Автотоварів, тому він нам не потрібен");
        }
    }
}
