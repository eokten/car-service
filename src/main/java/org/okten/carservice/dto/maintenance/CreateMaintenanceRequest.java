package org.okten.carservice.dto.maintenance;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateMaintenanceRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;;

    @Min(value = 10)
    private Double price;
}
