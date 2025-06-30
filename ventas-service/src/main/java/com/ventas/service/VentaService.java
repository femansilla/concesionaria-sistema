package com.ventas.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ventas.DTO.ClienteDTO;
import com.ventas.DTO.ServicioMecanicoDTO;
import com.ventas.DTO.TipoServicioMecanicoDTO;
import com.ventas.DTO.VehiculoDTO;
import com.ventas.DTO.TipoVehiculoDTO;
import com.ventas.DTO.VentaDTO;
import com.ventas.client.ClienteFeignClient;
import com.ventas.repository.VentaRepository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ventas.client.ServicioMecanicoFeignClient;
import com.ventas.client.VehiculoFeignClient;
import com.ventas.model.Venta;
import com.ventas.model.VentaRequest;
import com.ventas.model.VentaResponse;

@Service
@AllArgsConstructor
public class VentaService {

    private final VentaRepository repository;
    private final ClienteFeignClient clienteClient;
    private final VehiculoFeignClient vehiculoClient;
    private final ServicioMecanicoFeignClient servicioMecanicoClient;

    public VentaResponse save(VentaRequest ventaRequest) {
        Venta venta = new Venta();
        venta.setClienteId(ventaRequest.getClienteId());
        venta.setEmpleadoId(ventaRequest.getEmpleadoId());
        venta.setFechaOperacion(LocalDate.now());

        // Validar Cliente
        ClienteDTO cliente = safeGetCliente(ventaRequest.getClienteId());

        if (ventaRequest.getServicioMecanicoId() != null) {
            // Venta de servicio mecánico
            ServicioMecanicoDTO servicioMecanico = safeGetServicioMecanico(ventaRequest.getServicioMecanicoId());

            if (ventaRequest.getTipoServicioMecanicoId() == null) {
                throw new IllegalArgumentException("Debe especificar el tipo de servicio mecánico.");
            }

            TipoServicioMecanicoDTO tipoServicio = safeGetTipoServicioMecanico(ventaRequest.getTipoServicioMecanicoId());

            if (servicioMecanico.getVehiculoId() == null) {
                throw new IllegalArgumentException("El servicio mecánico no está asociado a un vehículo.");
            }

            VehiculoDTO vehiculo = safeGetVehiculo(servicioMecanico.getVehiculoId());
            TipoVehiculoDTO tipo = safeGetTipoVehiculo(vehiculo.getTipoVehiculoId());

            BigDecimal precioServicio = tipoServicio.getPrecio();
            Double adicional = tipo.getAdicionalServicio();
            BigDecimal monto = precioServicio.multiply(BigDecimal.valueOf(adicional));

            venta.setMonto(monto);
            venta.setCantidad(1);
            venta.setServicioMecanicoId(ventaRequest.getServicioMecanicoId());
            venta.setVehiculoId(servicioMecanico.getVehiculoId());

        } else if (ventaRequest.getVehiculoId() != null) {
            // Venta de vehículo
            if (ventaRequest.getCantidad() == null || ventaRequest.getCantidad() < 1) {
                throw new IllegalArgumentException("Debe especificar una cantidad válida.");
            }

            VehiculoDTO vehiculo = safeGetVehiculo(ventaRequest.getVehiculoId());

            // Validar stock disponible
            Map<String, Object> stockInfo = safeGetStock(ventaRequest.getVehiculoId(), ventaRequest.getConcesionariaId());

            int disponible = (int) stockInfo.get("stock");
            if (disponible < ventaRequest.getCantidad()) {
                throw new IllegalStateException("No hay suficiente stock disponible.");
            }

            BigDecimal monto = vehiculo.getPrecioUnidad().multiply(BigDecimal.valueOf(ventaRequest.getCantidad()));
            venta.setMonto(monto);
            venta.setCantidad(ventaRequest.getCantidad());
            venta.setVehiculoId(ventaRequest.getVehiculoId());

        } else {
            throw new IllegalArgumentException("La venta debe incluir un vehículo o un servicio mecánico.");
        }

        Venta saved = repository.save(venta);
        return getVentaEnriquecida(saved);
    }

    public List<VentaResponse> findAll() {
        return repository.findAll().stream()
            .map(this::getVentaEnriquecida)
            .collect(Collectors.toList());
    }

    public Optional<VentaResponse> findById(Long id) {
        return repository.findById(id)
                .map(this::getVentaEnriquecida);
    }

    private VentaResponse getVentaEnriquecida(Venta venta) {
        VentaResponse dto = new VentaResponse();
        BeanUtils.copyProperties(venta, dto);

        dto.setCliente(safeGetCliente(venta.getClienteId()));

        if (venta.getVehiculoId() != null) {
            dto.setVehiculo(safeGetVehiculo(venta.getVehiculoId()));
        }

        if (venta.getServicioMecanicoId() != null) {
            dto.setServicioMecanico(safeGetServicioMecanico(venta.getServicioMecanicoId()));
        }

        return dto;
    }

    // Métodos auxiliares con manejo de excepciones Feign
    private ClienteDTO safeGetCliente(Long id) {
        try {
            return clienteClient.getClienteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se pudo obtener el cliente con ID " + id + ": " + e.getMessage());
        }
    }

    private VehiculoDTO safeGetVehiculo(Long id) {
        try {
            return vehiculoClient.getVehiculoById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se pudo obtener el vehículo con ID " + id + ": " + e.getMessage());
        }
    }

    private TipoVehiculoDTO safeGetTipoVehiculo(Long id) {
        try {
            return vehiculoClient.getTipoVehiculoById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se pudo obtener el tipo de vehiculo con ID " + id + ": " + e.getMessage());
        }
    }

    private ServicioMecanicoDTO safeGetServicioMecanico(Long id) {
        try {
            return servicioMecanicoClient.getServicioById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se pudo obtener el servicio mecánico con ID " + id + ": " + e.getMessage());
        }
    }

    private TipoServicioMecanicoDTO safeGetTipoServicioMecanico(Long id) {
        try {
            return servicioMecanicoClient.getTipoServicioById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se pudo obtener el tipo de servicio mecánico con ID " + id + ": " + e.getMessage());
        }
    }

    private Map<String, Object> safeGetStock(Long vehiculoId, Long concesionariaId) {
        try {
            return vehiculoClient.getStockVehiculo(vehiculoId, concesionariaId);
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo consultar stock: " + e.getMessage());
        }
    }
}
