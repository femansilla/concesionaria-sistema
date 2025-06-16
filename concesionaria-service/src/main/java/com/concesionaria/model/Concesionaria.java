package com.concesionaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Concesionaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;

    @NotNull(message = "La fecha de apertura es obligatoria")
    @PastOrPresent(message = "La fecha de apertura no puede ser futura")
    private LocalDate fechaApertura;

    @ManyToOne
    @JoinColumn(name = "pais_id", nullable = false)
    @NotNull(message = "El país es obligatorio")
    private Pais pais;

    @ManyToOne
    @JoinColumn(name = "provincia_id", nullable = false)
    @NotNull(message = "La provincia es obligatoria")
    private Provincia provincia;

    @ManyToOne
    @JoinColumn(name = "localidad_id", nullable = false)
    @NotNull(message = "La localidad es obligatoria")
    private Localidad localidad;
}