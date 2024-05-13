package org.okten.carservice.properties;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FuelType {

    private String name;

    private List<String> variants;
}
