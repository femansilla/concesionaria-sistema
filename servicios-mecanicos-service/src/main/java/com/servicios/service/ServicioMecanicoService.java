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

    public List<ServicioMecanico> buscarFiltrado(Long clienteId, Long vehiculoId, Boolean enGarantia) {
        return repository.findAll().stream()
                .filter(s -> clienteId == null || s.getClienteId().equals(clienteId))
                .filter(s -> vehiculoId == null || s.getVehiculoId().equals(vehiculoId))
                .filter(s -> enGarantia == null || s.getEnGarantia().equals(enGarantia))
                .toList();
    }

}
