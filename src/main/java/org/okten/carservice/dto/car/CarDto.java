package org.okten.carservice.dto.car;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CarDto {

    private Long id;

    private String model;

    private Integer enginePower;

    private Integer torque;

    private String fuelType;

    private String owner;

    private LocalDate lastMaintenanceTimestamp;
}
