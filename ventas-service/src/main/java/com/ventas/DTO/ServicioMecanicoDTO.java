package com.ventas.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private Long clienteId;
    private Long vehiculoId;
    private Long tipoServicioId;
    private Integer kilometros;
    private Boolean enGarantia;
    private LocalDate fechaEntrega;
}
