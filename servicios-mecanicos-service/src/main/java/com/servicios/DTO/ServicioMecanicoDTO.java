
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicioMecanicoDTO {
    private Long id;
    private Long clienteId;
    private Long vehiculoId;
    private Long tipoServicioId;
    private LocalDate fechaEntrega;
    private Integer kilometros;
    private Boolean enGarantia;
    private TipoServicioMecanicoDTO tipoServicio; // para respuestas
}