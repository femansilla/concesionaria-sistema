package com.servicios.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicios.client.ClienteFeignClient;
import com.servicios.client.VehiculoFeignClient;
import com.servicios.model.ClienteDTO;
import com.servicios.model.ServicioMecanico;
import com.servicios.model.TipoServicioMecanico;
import com.servicios.model.VehiculoDTO;
import com.servicios.repository.ServicioMecanicoRepository;
import com.servicios.repository.TipoServicioMecanicoRepository;

@Service
@RequiredArgsConstructor
public class ServicioMecanicoService {

    @Autowired
    private final ServicioMecanicoRepository repository;
    @Autowired
    private final TipoServicioMecanicoRepository tiposRepository;
    private final ServicioMecanicoMapper mapper;
    private final ClienteFeignClient clienteClient;
    private final VehiculoFeignClient vehiculoClient;

    public ServicioMecanicoService(
        ServicioMecanicoRepository repository,
        ClienteFeignClient clienteClient,
        VehiculoFeignClient vehiculoClient,
        TipoServicioMecanicoRepository tiposRepository
    ) {
        this.repository = repository;
        this.clienteClient = clienteClient;
        this.vehiculoClient = vehiculoClient;
        this.tiposRepository = tiposRepository;
    }

    public ServicioMecanico crearServicio(ServicioMecanicoDTO dto) {
        TipoServicioMecanico tipo = tipoRepo.findById(dto.getTipoServicioId())
            .orElseThrow(() -> new IllegalArgumentException("Tipo de servicio no válido"));
        ServicioMecanico entity = mapper.toEntity(dto, tipo);
        return repository.save(entity);
    }

    public ServicioMecanico crearServicio(ServicioMecanico servicio) {
        // Validaciones cruzadas
        ClienteDTO cliente = clienteClient.getClienteById(servicio.getClienteId());
        VehiculoDTO vehiculo = vehiculoClient.getVehiculoById(servicio.getVehiculoId());

        if (cliente == null || vehiculo == null) {
            throw new RuntimeException("Cliente o vehículo no válido");
        }

        Long tipoServicioId = servicio.getServicio().getId();
        if(tipoServicioId == null)
            tipoServicioId = servicio.getTipoServicioId();

        TipoServicioMecanico tipo = tiposRepository.findById(tipoServicioId)
                .orElseThrow(() -> new RuntimeException("Tipo de servicio no encontrado"));

        servicio.setServicio(tipo);
        
        // Acceso al tipo de vehículo
        Integer garantiaAnios = vehiculo.getTipoVehiculo().getGarantiaAnios();
        Integer garantiaKm = vehiculo.getTipoVehiculo().getGarantiaKilometros();

        // Evaluación de garantía
        boolean enGarantia = false;
        int aniosTranscurridos = LocalDate.now().getYear() - vehiculo.getAnio();
        
        if (servicio.getKilometros() != null && servicio.getKilometros() <= garantiaKm) {
            enGarantia = true;
        } else if (aniosTranscurridos <= garantiaAnios) {
            enGarantia = true;
        }

        // Asignar automáticamente el valor de garantía
        servicio.setEnGarantia(enGarantia);

        return repository.save(servicio);
    }

    public List<ServicioMecanico> findAll() {
        return repository.findAll();
    }

    public Optional<ServicioMecanico> findById(Long id) {
        return repository.findById(id);
    }

    public List<ServicioMecanico> buscarFiltrado(Long clienteId, Long vehiculoId, Boolean enGarantia, Long tipoServicioId) {
        return repository.findAll().stream()
                .filter(s -> clienteId == null || s.getClienteId().equals(clienteId))
                .filter(s -> vehiculoId == null || s.getVehiculoId().equals(vehiculoId))
                .filter(s -> enGarantia == null || s.getEnGarantia().equals(enGarantia))
                .filter(s -> tipoServicioId == null || s.getServicio().getId().equals(tipoServicioId))
                .toList();
    }

}
