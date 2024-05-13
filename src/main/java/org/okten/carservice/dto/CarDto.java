package org.okten.carservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDto {

    private Long id;

    private String model;

    private Integer enginePower;

    private Integer torque;

    private String fuelType;
}
