package com.vehiculos.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank
    private String descripcion;

    @NotNull
    @Min(0)
    private Integer garantiaAnios;

    @NotNull
    @Min(0)
    private Integer garantiaKilometros;

    @NotNull
    @Min(1)
    private Double adicionalServicio;
}
