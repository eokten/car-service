package org.okten.carservice.dto.request;

import lombok.Builder;
import lombok.Data;
import org.okten.carservice.validation.ValidFuelType;

@Data
@Builder
public class UpdateCarRequest {

    private String model;

    private Integer enginePower;

    private Integer torque;

    @ValidFuelType
    private String fuelType;
}
