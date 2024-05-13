package org.okten.carservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FuelTypeValidator.class)
public @interface ValidFuelType {

    boolean allowNullableValue() default true;

    String message() default "must match any value from fuel types in configuration properties";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
