package com.servicios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.servicios.DTO.TipoVehiculoDTO;
import com.servicios.DTO.VehiculoDTO;

@FeignClient(name = "vehiculos-stock-service")
public interface VehiculoFeignClient {
    @GetMapping("/vehiculos/{id}")
    VehiculoDTO getVehiculoById(@PathVariable("id") Long id);

    @GetMapping("/vehiculos/tipos/{tipoId}")
    TipoVehiculoDTO getTipoVehiculoById(@PathVariable("tipoId") Long tipoId);
}
