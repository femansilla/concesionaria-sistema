package com.vehiculos.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcesionariaDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private LocalDate fechaApertura;
    private Long paisId;
    private Long provinciaId;
    private Long localidadId;

}