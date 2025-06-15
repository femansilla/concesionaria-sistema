package main.java.com.servicios.model;

import jakarta.persistence.*;
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

    private Long clienteId;
    private Long vehiculoId;

    private LocalDate fechaEntrega;
    private Integer kilometros;
    private Boolean enGarantia;
}