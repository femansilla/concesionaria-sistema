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
public class StockDTO {
    private Long id;

    @NotNull
    private Long vehiculoId;

    private Long concesionariaId;

    @NotNull
    @Min(0)
    private Integer cantidad;

    @NotNull
    @Min(0)
    private Integer tiempoEntregaDias;

    @NotNull
    @Min(0)
    private Integer tiempoDesdeCentralDias;
}
