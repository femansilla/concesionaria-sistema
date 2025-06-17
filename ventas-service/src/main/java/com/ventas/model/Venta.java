package com.ventas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "El ID del empleado es obligatorio")
    private Long empleadoId;

    @NotNull(message = "El ID del vehículo es obligatorio")
    private Long vehiculoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    private BigDecimal monto;

    @NotNull(message = "La fecha de operación es obligatoria")
    @PastOrPresent(message = "La fecha de operación no puede ser futura")
    private LocalDate fechaOperacion;

    // Relación opcional con servicio mecánico (si la venta se originó por uno)
    private Long servicioMecanicoId;
}