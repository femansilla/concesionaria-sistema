package com.ventas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ventas.model.VehiculoDTO;

@FeignClient(name = "vehiculos-stock-service")
public interface VehiculoFeignClient {
    @GetMapping("/vehiculos/{id}")
    VehiculoDTO getVehiculoById(@PathVariable("id") Long id);
}
