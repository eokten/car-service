package org.okten.carservice.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@Document("maintenances")
public class Maintenance {

    @MongoId
    private ObjectId id;

    private String name;

    private String description;

    private Double price;
}
