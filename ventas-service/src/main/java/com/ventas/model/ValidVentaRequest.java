package com.ventas.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VentaRequestValidator.class)
public @interface ValidVentaRequest {
    String message() default "Si se especifica un tipo de servicio, debe indicarse el kilometraje";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

