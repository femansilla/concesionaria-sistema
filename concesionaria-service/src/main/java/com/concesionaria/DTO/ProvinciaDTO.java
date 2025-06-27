package com.concesionaria.DTO;

import jakarta.persistence.ManyToOne;
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
public class ProvinciaDTO {
    private Long id;

    @NotBlank
    private String nombre;

    @ManyToOne
    private Long provinciaId;

    @NotNull
    private Long paisId;
}
