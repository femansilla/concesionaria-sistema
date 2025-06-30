package com.ventas.model;

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
public class VentaRequest {
    @NotNull
    private Long clienteId;
    @NotNull
    private Long empleadoId;

    // Si es venta de vehículo
    private Long vehiculoId;
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;
    private Long concesionariaId;

    // Si es venta de servicio
    private Long servicioMecanicoId;
    private Long tipoServicioMecanicoId;
}
