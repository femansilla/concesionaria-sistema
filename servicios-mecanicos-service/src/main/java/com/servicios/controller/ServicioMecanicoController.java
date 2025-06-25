package com.servicios.controller;

import com.servicios.DTO.ServicioMecanicoDTO;
import com.servicios.service.ServicioMecanicoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicios")
@RequiredArgsConstructor
public class ServicioMecanicoController {

    @Autowired
    private final ServicioMecanicoService servicio;
    private final ServicioMecanicoMapper mapper;

    public ServicioMecanicoController(ServicioMecanicoService servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody @Valid ServicioMecanico ServicioMecanicoDTO dto) {
        try {

            ServicioMecanico creado = servicio.crearServicio(dto);
            return ResponseEntity.ok(mapper.toDTO(creado));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<ServicioMecanico> findAll() {

        return ResponseEntity.ok(servicio.findAll().stream()
                .map(mapper::toDTO).toList());

    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioMecanico> findById(@PathVariable("id") Long id) {
        return servicio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<ServicioMecanico>> filtrar(
            @RequestParam(name = "clienteId", required = false) Long clienteId,
            @RequestParam(name = "vehiculoId", required = false) Long vehiculoId,
            @RequestParam(name = "enGarantia", required = false) Boolean enGarantia,
            @RequestParam(name = "tipoServicioId", required = false) Long tipoServicioId) {

        List<ServicioMecanico> resultados = servicio.buscarFiltrado(clienteId, vehiculoId, enGarantia, tipoServicioId);
        return ResponseEntity.ok(resultados);
    }

}
