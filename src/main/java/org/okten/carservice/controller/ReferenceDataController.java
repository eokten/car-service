package org.okten.carservice.controller;

import lombok.RequiredArgsConstructor;
import org.okten.carservice.properties.FuelType;
import org.okten.carservice.properties.ReferenceDataProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReferenceDataController {

    @Value("${reference-data.engine-types}")
    private List<String> engineTypes;

    @Autowired
    private ReferenceDataProperties referenceDataProperties;

    @GetMapping("/engine-types")
    public ResponseEntity<List<String>> getEngineTypes() {
        return ResponseEntity.ok(engineTypes);
    }

    @GetMapping("/fuel-types")
    public ResponseEntity<List<FuelType>> getFuelTypes() {
        return ResponseEntity.ok(referenceDataProperties.getFuelTypes());
    }

    @GetMapping("/fuel-types/{fuel-type-name}")
    public ResponseEntity<FuelType> getFuelType(@PathVariable(required = true, name = "fuel-type-name") String fuelTypeName) {
        return ResponseEntity.of(referenceDataProperties
                .getFuelTypes()
                .stream()
                .filter(fuelType -> fuelType.getName().equals(fuelTypeName))
                .findFirst());
    }
}
