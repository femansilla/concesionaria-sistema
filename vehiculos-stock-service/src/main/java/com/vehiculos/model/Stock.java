package com.vehiculos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Vehiculo vehiculo;

    private Long concesionariaId;

    private Integer cantidad;
    private Integer tiempoEntregaDias;
    private Integer tiempoDesdeCentralDias;
}
