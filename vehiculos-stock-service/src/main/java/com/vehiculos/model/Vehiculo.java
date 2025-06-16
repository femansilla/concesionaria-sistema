package com.vehiculos.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    @NotBlank(message = "El tipo de vehículo es obligatorio")
    private String tipo; // SUV, Sedan, Pickup, etc.

    @NotNull(message = "El año es obligatorio")
    @Min(value = 1900, message = "El año debe ser mayor a 1900")
    @Max(value = 2100, message = "El año no puede superar el 2100")
    private Integer anio;

    @NotNull(message = "Años de garantía es obligatorio")
    @Min(value = 0, message = "La garantía debe ser cero o más")
    private Integer garantiaAnios;

    @NotNull(message = "Kilómetros de garantía es obligatorio")
    @Min(value = 0, message = "Los kilómetros de garantía deben ser cero o más")
    private Integer garantiaKilometros;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private BigDecimal precioUnidad;
}