package com.servicios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.servicios.model.VehiculoDTO;

@FeignClient(name = "vehiculos-service")
public interface VehiculoFeignClient {
    @GetMapping("/vehiculos/{id}")
    VehiculoDTO getVehiculoById(@PathVariable("id") Long id);
}
