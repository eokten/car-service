package org.okten.carservice.dto.maintenance;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateMaintenanceRequest {

    private String name;

    private String description;

    private Double price;
}
