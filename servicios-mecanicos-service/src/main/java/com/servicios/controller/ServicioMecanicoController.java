package com.servicios.controller;

import com.servicios.model.ServicioMecanico;
import com.servicios.repository.ServicioMecanicoRepository;
import com.servicios.service.ServicioMecanicoService;

import jakarta.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/servicios")
public class ServicioMecanicoController {

    private final ServicioMecanicoService servicio;

    public ServicioMecanicoController(ServicioMecanicoService servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody @Valid ServicioMecanico servicioMecanico) {
        try {
            ServicioMecanico creado = servicio.crearServicio(servicioMecanico);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<ServicioMecanico> findAll() {
        return servicio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioMecanico> findById(@PathVariable Long id) {
        return servicio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<ServicioMecanico>> filtrar(
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) Long vehiculoId,
            @RequestParam(required = false) Boolean enGarantia,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {

        List<ServicioMecanico> resultados = servicio.buscarFiltrado(clienteId, vehiculoId, enGarantia, desde, hasta);
        return ResponseEntity.ok(resultados);
    }

}
