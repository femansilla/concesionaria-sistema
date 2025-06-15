package com.concesionaria.model;

import jakarta.persistence.*;
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

    private String nombre;
    private String direccion;
    private LocalDate fechaApertura;

    @ManyToOne
    private Pais pais;

    @ManyToOne
    private Localidad localidad;
}