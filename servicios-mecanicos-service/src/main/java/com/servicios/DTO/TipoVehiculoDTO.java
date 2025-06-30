package com.servicios.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoVehiculoDTO {
    private Long id;
    private String descripcion;
    private Integer garantiaAnios;
    private Integer garantiaKilometros;
}