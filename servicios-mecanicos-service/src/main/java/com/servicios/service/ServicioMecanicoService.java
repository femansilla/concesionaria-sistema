package com.servicios.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.servicios.client.ClienteFeignClient;
import com.servicios.client.VehiculoFeignClient;
import com.servicios.model.ClienteDTO;
import com.servicios.model.ServicioMecanico;
import com.servicios.model.VehiculoDTO;
import com.servicios.repository.ServicioMecanicoRepository;

@Service
public class ServicioMecanicoService {

    private final ServicioMecanicoRepository repository;
    private final ClienteFeignClient clienteClient;
    private final VehiculoFeignClient vehiculoClient;

    public ServicioMecanicoService(
        ServicioMecanicoRepository repository,
        ClienteFeignClient clienteClient,
        VehiculoFeignClient vehiculoClient
    ) {
        this.repository = repository;
        this.clienteClient = clienteClient;
        this.vehiculoClient = vehiculoClient;
    }

    public ServicioMecanico crearServicio(ServicioMecanico servicio) {
        // Validaciones cruzadas
        ClienteDTO cliente = clienteClient.getClienteById(servicio.getClienteId());
        VehiculoDTO vehiculo = vehiculoClient.getVehiculoById(servicio.getVehiculoId());

        if (cliente == null || vehiculo == null) {
            throw new RuntimeException("Cliente o vehículo no válido");
        }

        return repository.save(servicio);
    }

    public List<ServicioMecanico> findAll() {
        return repository.findAll();
    }

    public Optional<ServicioMecanico> findById(Long id) {
        return repository.findById(id);
    }

    public List<ServicioMecanico> buscarFiltrado(Long clienteId, Long vehiculoId, Boolean enGarantia,
                                             LocalDate desde, LocalDate hasta) {
        return repository.findAll().stream()
                .filter(s -> clienteId == null || s.getClienteId().equals(clienteId))
                .filter(s -> vehiculoId == null || s.getVehiculoId().equals(vehiculoId))
                .filter(s -> enGarantia == null || s.getEnGarantia().equals(enGarantia))
                .filter(s -> desde == null || !s.getFechaEntrega().isBefore(desde))
                .filter(s -> hasta == null || !s.getFechaEntrega().isAfter(hasta))
                .toList();
    }

}
