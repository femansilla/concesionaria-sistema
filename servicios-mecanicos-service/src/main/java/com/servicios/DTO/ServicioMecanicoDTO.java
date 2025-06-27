package com.servicios.DTO;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicioMecanicoDTO {
    private Long id;
    @NotNull
    private Long clienteId;
    @NotNull
    private Long vehiculoId;
    @NotNull
    private Long tipoServicioId;
    @NotNull
    private LocalDate fechaEntrega;
    @NotNull
    @Min(0)
    private Integer kilometros;
    @NotNull
    private Boolean enGarantia;
}