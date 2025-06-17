package com.vehiculos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vehiculos.model.ConcesionariaDTO;

@FeignClient(name = "concesionaria-service", path = "/concesionarias")
public interface ConcesionariaFeignClient {

    @GetMapping("/{id}")
    ConcesionariaDTO obtenerConcesionaria(@PathVariable("id") Long id);
}
