package com.servicios.DTO;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
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
public class TipoServicioMecanicoDTO {
    private Long id;
    @NotBlank
    private String descripcion;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal precio;
    @NotNull
    @Min(1)
    private Integer cantDiasServicio;
}