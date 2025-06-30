package com.ventas.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ventas.DTO.ClienteDTO;
import com.ventas.DTO.ServicioMecanicoDTO;
import com.ventas.DTO.VehiculoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaResponse {
    private Long id;
    private Long clienteId;
    private Long empleadoId;
    private Long vehiculoId;
    private Integer cantidad;
    private BigDecimal monto;
    private LocalDate fechaOperacion;
    private Long servicioMecanicoId;

    // Datos enriquecidos
    private ClienteDTO cliente;
    private VehiculoDTO vehiculo;
    private ServicioMecanicoDTO servicioMecanico;
}
