package com.vehiculos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoVehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El tipo de vehículo es obligatorio")
    private String descripcion; // SUV, Sedan, Pickup, etc.
    
    @NotNull(message = "Años de garantía es obligatorio")
    @Min(value = 0, message = "La garantía debe ser cero o más")
    private Integer garantiaAnios;

    @NotNull(message = "Kilómetros de garantía es obligatorio")
    @Min(value = 0, message = "Los kilómetros de garantía deben ser cero o más")
    private Integer garantiaKilometros;
}
