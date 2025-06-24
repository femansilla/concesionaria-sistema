package com.ventas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ventas.model.ServicioMecanicoDTO;
import com.ventas.model.TipoServicioMecanicoDTO;

@FeignClient(name = "servicios-mecanicos-service")
public interface ServicioMecanicoFeignClient {
    @GetMapping("/servicios/{id}")
    ServicioMecanicoDTO getServicioById(@PathVariable("id") Long id);

    @GetMapping("servicios/tipos/{id}")
    TipoServicioMecanicoDTO getTipoServicioById(@PathVariable("id") Long id);

    @PostMapping("/servicios")
    ServicioMecanicoDTO crearServicio(@RequestBody ServicioMecanicoDTO servicio);
}

