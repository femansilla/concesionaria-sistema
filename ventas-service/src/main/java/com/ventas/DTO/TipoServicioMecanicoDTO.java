package com.ventas.DTO;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoServicioMecanicoDTO {
    private Long id;
    private String descripcion; // Cambio de aceite y filtros, Revisión y ajuste de frenos, Inspección y rotación de neumáticos, etc.
    private BigDecimal precio;
    private Integer cantDiasServicio;
}
