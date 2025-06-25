

@Component
public class ServicioMecanicoMapper {
    @Autowired
    private ModelMapper modelMapper;

    public ServicioMecanicoDTO toDTO(ServicioMecanico entity) {
        ServicioMecanicoDTO dto = modelMapper.map(entity, ServicioMecanicoDTO.class);
        dto.setTipoServicioId(entity.getServicio().getId());
        dto.setTipoServicio(modelMapper.map(entity.getServicio(), TipoServicioMecanicoDTO.class));
        return dto;
    }

    public ServicioMecanico toEntity(ServicioMecanicoDTO dto, TipoServicioMecanico tipoServicio) {
        ServicioMecanico entity = modelMapper.map(dto, ServicioMecanico.class);
        entity.setServicio(tipoServicio);
        return entity;
    }
}