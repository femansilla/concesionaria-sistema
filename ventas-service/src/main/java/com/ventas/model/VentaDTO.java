package com.ventas.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaDTO {
    private Long id;
    private Integer cantidad;
    private BigDecimal monto;
    private LocalDate fechaOperacion;

    private ClienteDTO cliente;
    private VehiculoDTO vehiculo;
    private ServicioMecanicoDTO servicioMecanico; // puede ser null si es venta de vehículo
}
