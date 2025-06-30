package com.ventas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ventas.DTO.ClienteDTO;

@FeignClient(name = "clientes-service")
public interface ClienteFeignClient {
    @GetMapping("/clientes/{id}")
    ClienteDTO getClienteById(@PathVariable("id") Long id);
}