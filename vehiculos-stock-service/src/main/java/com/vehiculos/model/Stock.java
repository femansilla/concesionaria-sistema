package com.vehiculos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

// Cuando concesionariaId == null → representa stock central
// Cuando concesionariaId != null → representa stock de una concesionaria
// Reglas a implementar en el servicio de ventas
// Si la concesionaria tiene stock del vehículo → usar ese stock y aplicar tiempoEntregaDias
// Si no tiene → verificar si el stock central tiene unidades → descontar ahí, y aplicar tiempoDesdeCentralDias

public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull(message = "Debe asociarse un vehículo al stock")
    private Vehiculo vehiculo;

    // Si es null, representa stock central
    private Long concesionariaId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Integer cantidad;

    @NotNull(message = "El tiempo de entrega es obligatorio")
    @Min(value = 0, message = "El tiempo de entrega debe ser cero o más")
    private Integer tiempoEntregaDias; // Tiempo desde que se tiene stock hasta la entrega

    @NotNull(message = "El tiempo desde la central es obligatorio")
    @Min(value = 0, message = "El tiempo desde la central debe ser cero o más")
    private Integer tiempoDesdeCentralDias; // Si no hay stock, cuánto tarda en llegar desde el central
}
