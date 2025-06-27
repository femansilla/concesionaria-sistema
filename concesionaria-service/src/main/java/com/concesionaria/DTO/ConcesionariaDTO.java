package com.concesionaria.DTO;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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

    @NotBlank
    private String nombre;

    @NotBlank
    private String direccion;

    @NotNull
    @PastOrPresent
    private LocalDate fechaApertura;

    @NotNull
    private Long paisId;

    @NotNull
    private Long provinciaId;

    @NotNull
    private Long localidadId;
}
