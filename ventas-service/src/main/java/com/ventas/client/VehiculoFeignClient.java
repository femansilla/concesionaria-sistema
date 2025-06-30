package com.ventas.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ventas.DTO.TipoVehiculoDTO;
import com.ventas.DTO.VehiculoDTO;

@FeignClient(name = "vehiculos-stock-service")
public interface VehiculoFeignClient {
    @GetMapping("/vehiculos/{vehiculoId}")
    VehiculoDTO getVehiculoById(@PathVariable("vehiculoId") Long vehiculoId);

    @GetMapping("/vehiculos/tipos/{tipoId}")
    TipoVehiculoDTO getTipoVehiculoById(@PathVariable("tipoId") Long tipoId);

    @GetMapping("/vehiculos/stock/vehiculo/{vehiculoId}")
    Map<String, Object> getStockVehiculo(@PathVariable("vehiculoId") Long vehiculoId, @RequestParam("concesionariaId") Long concesionariaId);
}
