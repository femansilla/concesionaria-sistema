
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoServicioMecanicoDTO {
    private Long id;
    private String descripcion;
    private BigDecimal precio;
    private Integer demoraDias;
}