package com.servicios.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoDTO {
    private Long id;
    private String marca;
    private String modelo;
    private Long tipoVehiculoId;
    private Integer anio;
    private BigDecimal precioUnidad;
}

