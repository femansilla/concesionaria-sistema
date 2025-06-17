package com.ventas.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ventas.client.ClienteFeignClient;
import com.ventas.repository.VentaRepository;
import com.ventas.client.ServicioMecanicoFeignClient;
import com.ventas.client.VehiculoFeignClient;
import com.ventas.model.Venta;
import com.ventas.model.VentaDTO;

@Service
public class VentaService {

    private final VentaRepository repository;
    private final ClienteFeignClient clienteClient;
    private final VehiculoFeignClient vehiculoClient;
    private final ServicioMecanicoFeignClient servicioMecanicoClient;

    public VentaService(
        VentaRepository repository,
        ClienteFeignClient clienteClient,
        VehiculoFeignClient vehiculoClient,
        ServicioMecanicoFeignClient servicioMecanicoClient
    ) {
        this.repository = repository;
        this.clienteClient = clienteClient;
        this.vehiculoClient = vehiculoClient;
        this.servicioMecanicoClient = servicioMecanicoClient;
    }

    public Venta save(Venta venta) {
        return repository.save(venta);
    }

    public List<VentaDTO> findAll() {
        return repository.findAll().stream()
            .map(this::getVentaEnriquecida)
            .collect(Collectors.toList());
    }

    public Optional<VentaDTO> findById(Long id) {
        return repository.findById(id)
            .map(this::getVentaEnriquecida);
    }

    private VentaDTO getVentaEnriquecida(Venta venta) {
        VentaDTO dto = new VentaDTO();
        BeanUtils.copyProperties(venta, dto);

        dto.setCliente(clienteClient.getClienteById(venta.getClienteId()));
        dto.setVehiculo(vehiculoClient.getVehiculoById(venta.getVehiculoId()));

        if (venta.getServicioMecanicoId() != null) {
            dto.setServicioMecanico(servicioMecanicoClient.getServicioById(venta.getServicioMecanicoId()));
        }

        return dto;
    }
}
