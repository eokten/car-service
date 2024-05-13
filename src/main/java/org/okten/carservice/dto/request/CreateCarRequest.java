package org.okten.carservice.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.okten.carservice.validation.ValidFuelType;

@Data
@Builder
public class CreateCarRequest {

    @NotBlank
    private String model;

    @NotNull
    @Min(value = 10)
    @Max(value = 1000)
    private Integer enginePower;

    @NotNull
    @Min(value = 50)
    @Max(value = 900)
    private Integer torque;

    @ValidFuelType(allowNullableValue = false)
    private String fuelType;
}
