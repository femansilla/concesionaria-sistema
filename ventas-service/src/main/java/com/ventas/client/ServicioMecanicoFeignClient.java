package com.ventas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ventas.model.ServicioMecanicoDTO;

@FeignClient(name = "servicios-mecanicos-service")
public interface ServicioMecanicoFeignClient {
    @GetMapping("/servicios/{id}")
    ServicioMecanicoDTO getServicioById(@PathVariable("id") Long id);
}

