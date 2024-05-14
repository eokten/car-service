package org.okten.carservice.dto.maintenance;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@Builder
public class MaintenanceDto {

    private String id;

    private String name;

    private String description;

    private Double price;
}
