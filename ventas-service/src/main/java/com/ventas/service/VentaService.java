package com.ventas.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ventas.client.ClienteFeignClient;
import com.ventas.repository.VentaRepository;
import com.ventas.client.ServicioMecanicoFeignClient;
import com.ventas.client.VehiculoFeignClient;
import com.ventas.model.ServicioMecanicoDTO;
import com.ventas.model.TipoServicioMecanicoDTO;
import com.ventas.model.VehiculoDTO;
import com.ventas.model.Venta;
import com.ventas.model.VentaDTO;
import com.ventas.model.VentaRequest;

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

    public Venta save(VentaRequest ventaRequest) {
        Venta venta = ventaRequest.getVenta();

        if (ventaRequest.getTipoServicioMecanicoId() != null) {
            // Es una venta de servicio: crear registro en servicio-mecanico

            if (venta.getFechaOperacion() == null) {
                venta.setFechaOperacion(LocalDate.now());
            }

            TipoServicioMecanicoDTO tipoServicio = TipoServicioMecanicoDTO.builder()
                .id(ventaRequest.getTipoServicioMecanicoId())
                .build();
            
            ServicioMecanicoDTO servicio = ServicioMecanicoDTO.builder()
                .clienteId(venta.getClienteId())
                .vehiculoId(venta.getVehiculoId())
                .tipoServicioId(ventaRequest.getTipoServicioMecanicoId())
                .servicio(tipoServicio)
                //la fecha de entrega tiene que ser fecha_operacion + cant_dias_servicio_mecanico, esta definida en TipoServicioMecanico
                .fechaEntrega(venta.getFechaOperacion().plusDays(3))
                .kilometros(ventaRequest.getKilometros())
                .build();

            ServicioMecanicoDTO servicioCreado = servicioMecanicoClient.crearServicio(servicio);
            BigDecimal precioServicio = servicioCreado.getServicio().getPrecio();
            //el precio deberia ademar de estar determinado por el servicio, tambien por el tipo de vehiculo
            //  ej: pickup mas caro que sedan
            venta.setMonto(precioServicio.multiply(BigDecimal.valueOf(venta.getCantidad())));
            venta.setServicioMecanicoId(servicioCreado.getId());
        }

        if (venta.getVehiculoId() != null) {
            // Es una venta de vehículo: actualizar stock
            boolean actualizado = vehiculoClient.descontarStock(venta.getVehiculoId(), venta.getCantidad());
            if (!actualizado) {
                throw new IllegalStateException("No hay suficiente stock para el vehículo con ID: " + venta.getVehiculoId());
            }
            VehiculoDTO vehiculo = vehiculoClient.getVehiculoById(venta.getVehiculoId());
            BigDecimal total = vehiculo.getPrecioUnidad().multiply(BigDecimal.valueOf(venta.getCantidad()));
            venta.setMonto(total);
        } else {
            throw new IllegalArgumentException("La venta debe incluir un vehículo o un servicio.");
        }

        // Guardar la venta
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
