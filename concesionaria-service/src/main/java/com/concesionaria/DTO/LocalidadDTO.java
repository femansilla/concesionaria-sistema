package com.concesionaria.DTO;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalidadDTO {
    private Long id;

    @NotBlank
    private String nombre;

    @ManyToOne
    private Long provinciaId;
}
