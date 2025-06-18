package com.ventas.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ValidVentaRequest
public class VentaRequest {
    @Valid
    @NotNull(message = "La información de la venta es obligatoria")
    private Venta venta;

    // Solo requerido si es una venta de servicio mecánico
    private Long tipoServicioMecanicoId;

    @Min(value = 0, message = "El kilometraje no puede ser negativo")
    private Integer kilometros;
}
