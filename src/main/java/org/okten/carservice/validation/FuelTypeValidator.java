package org.okten.carservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.okten.carservice.properties.ReferenceDataProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FuelTypeValidator implements ConstraintValidator<ValidFuelType, String> {

    private final ReferenceDataProperties referenceDataProperties;

    private boolean allowNullableValue = false;

    @Override
    public void initialize(ValidFuelType constraintAnnotation) {
        allowNullableValue = constraintAnnotation.allowNullableValue();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return allowNullableValue && value == null || value != null && referenceDataProperties.getFuelTypes().stream().anyMatch(fuelType -> StringUtils.equalsIgnoreCase(fuelType.getName(), value));
    }
}
