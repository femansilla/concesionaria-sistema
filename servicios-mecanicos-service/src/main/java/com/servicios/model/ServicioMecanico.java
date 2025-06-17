package com.servicios.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioMecanico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "El ID del vehículo es obligatorio")
    private Long vehiculoId;

    @NotNull(message = "Debe seleccionar el tipo de servicio")
    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_servicio_id")
    private TipoServicioMecanico servicio;

    @NotNull(message = "La fecha de entrega es obligatoria")
    private LocalDate fechaEntrega;

    @NotNull(message = "Debe ingresar el kilometraje")
    @Min(value = 0, message = "El kilometraje no puede ser negativo")
    private Integer kilometros;

    @Column(nullable = false)
    private Boolean enGarantia;
}