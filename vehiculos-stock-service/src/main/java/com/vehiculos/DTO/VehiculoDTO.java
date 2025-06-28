package com.vehiculos.DTO;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
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
public class VehiculoDTO {
    private Long id;

    @NotBlank
    private String marca;

    @NotBlank
    private String modelo;

    @NotNull
    private Long tipoVehiculoId;

    @NotNull
    @Min(1900)
    @Max(2100)
    private Integer anio;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal precioUnidad;
}
