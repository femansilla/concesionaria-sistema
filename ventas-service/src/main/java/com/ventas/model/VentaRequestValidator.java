package com.ventas.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VentaRequestValidator implements ConstraintValidator<ValidVentaRequest, VentaRequest> {

    @Override
    public boolean isValid(VentaRequest request, ConstraintValidatorContext context) {
        if (request.getTipoServicioMecanicoId() != null && request.getKilometros() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Debe especificar el kilometraje para un servicio mecánico")
                   .addPropertyNode("kilometros")
                   .addConstraintViolation();
            return false;
        }
        return true;
    }
}

