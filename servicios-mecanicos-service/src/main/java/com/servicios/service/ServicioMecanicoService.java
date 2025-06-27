package com.servicios.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicios.DTO.ClienteDTO;
import com.servicios.DTO.ServicioMecanicoDTO;
import com.servicios.DTO.VehiculoDTO;
import com.servicios.client.ClienteFeignClient;
import com.servicios.client.VehiculoFeignClient;
import com.servicios.model.ServicioMecanico;
import com.servicios.model.TipoServicioMecanico;
import com.servicios.repository.ServicioMecanicoRepository;
import com.servicios.repository.TipoServicioMecanicoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioMecanicoService {

    @Autowired
    private final ServicioMecanicoRepository repository;
    @Autowired
    private final TipoServicioMecanicoRepository tiposRepository;
    private final ModelMapper mapper;
    private final ClienteFeignClient clienteClient;
    private final VehiculoFeignClient vehiculoClient;

    public ServicioMecanicoDTO crearServicio(ServicioMecanicoDTO dto) {
        // Validaciones cruzadas
        ClienteDTO cliente = clienteClient.getClienteById(dto.getClienteId());
        VehiculoDTO vehiculo = vehiculoClient.getVehiculoById(dto.getVehiculoId());
        if (cliente == null || vehiculo == null) {
            throw new RuntimeException("Cliente o vehículo no válido");
        }
        // Validación del tipo de servicio
        TipoServicioMecanico tipo = tiposRepository.findById(dto.getTipoServicioId())
                .orElseThrow(() -> new RuntimeException("Tipo de servicio no encontrado"));

        // Acceso al tipo de vehículo
        Integer garantiaAnios = vehiculo.getTipoVehiculo().getGarantiaAnios();
        Integer garantiaKm = vehiculo.getTipoVehiculo().getGarantiaKilometros();

        // Evaluación de garantía
        boolean enGarantia = false;
        int aniosTranscurridos = LocalDate.now().getYear() - vehiculo.getAnio();
        
        if (dto.getKilometros() != null && dto.getKilometros() <= garantiaKm) {
            enGarantia = true;
        } else if (aniosTranscurridos <= garantiaAnios) {
            enGarantia = true;
        }

        
        ServicioMecanico entidad = mapper.map(dto, ServicioMecanico.class);
        // asignar cliente y vehículo
        entidad.setClienteId(cliente.getId());  
        entidad.setVehiculoId(vehiculo.getId());
        // Asignar tipo de servicio
        entidad.setServicio(tipo);
        // Asignar automáticamente el valor de garantía
        entidad.setEnGarantia(enGarantia);
        // Validar fecha de entrega
        if (dto.getFechaEntrega() == null) {    
            throw new RuntimeException("La fecha de entrega es obligatoria");
        }   
        if (dto.getFechaEntrega().isBefore(LocalDate.now())) {
            throw new RuntimeException("La fecha de entrega no puede ser anterior a la fecha actual");
        }   
        // Validar kilometraje
        if (dto.getKilometros() == null || dto.getKilometros() < 0) {
            throw new RuntimeException("El kilometraje no puede ser negativo");
        }   
        
        return mapper.map(repository.save(entidad), ServicioMecanicoDTO.class);
    }

    public List<ServicioMecanicoDTO> findAll() {
        return repository.findAll().stream()
                .map(s -> {
                    ServicioMecanicoDTO dto = mapper.map(s, ServicioMecanicoDTO.class);
                    dto.setTipoServicioId(s.getServicio().getId());
                    return dto;
                }).toList();
    }

    public Optional<ServicioMecanicoDTO> findById(Long id) {
        return repository.findById(id)
                .map(s -> {
                    ServicioMecanicoDTO dto = mapper.map(s, ServicioMecanicoDTO.class);
                    dto.setTipoServicioId(s.getServicio().getId());
                    return dto;
                });
    }

    public List<ServicioMecanicoDTO> buscarFiltrado(Long clienteId, Long vehiculoId, Boolean enGarantia, Long tipoServicioId) {
        return repository.buscarConFiltros(clienteId, vehiculoId, enGarantia, tipoServicioId)
                .stream()
                .map(s -> {
                    ServicioMecanicoDTO dto = mapper.map(s, ServicioMecanicoDTO.class);
                    dto.setTipoServicioId(s.getServicio().getId());
                    return dto;
                }).toList();
    }

}
