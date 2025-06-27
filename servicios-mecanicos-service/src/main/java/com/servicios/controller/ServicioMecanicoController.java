package com.servicios.controller;

import com.servicios.DTO.ServicioMecanicoDTO;
import com.servicios.service.ServicioMecanicoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicios")
@RequiredArgsConstructor
public class ServicioMecanicoController {

    @Autowired
    private final ServicioMecanicoService servicio;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody @Valid ServicioMecanicoDTO dto) {
        try {
            ServicioMecanicoDTO creado = servicio.crearServicio(dto);
            return ResponseEntity.ok(creado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ServicioMecanicoDTO>> findAll() {
        return ResponseEntity.ok(servicio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioMecanicoDTO> findById(@PathVariable("id") Long id) {
        return servicio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<ServicioMecanicoDTO>> filtrar(
            @RequestParam(name = "clienteId", required = false) Long clienteId,
            @RequestParam(name = "vehiculoId", required = false) Long vehiculoId,
            @RequestParam(name = "enGarantia", required = false) Boolean enGarantia,
            @RequestParam(name = "tipoServicioId", required = false) Long tipoServicioId) {
        return ResponseEntity.ok(servicio.buscarFiltrado(clienteId, vehiculoId, enGarantia, tipoServicioId));
    }

}
