package com.concesionaria.controller;

import com.concesionaria.DTO.ConcesionariaDTO;
import com.concesionaria.model.Concesionaria;
import com.concesionaria.repository.ConcesionariaRepository;
import com.concesionaria.service.ConcesionariaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/concesionarias")
@RequiredArgsConstructor
public class ConcesionariaController {

    private final ConcesionariaService service;

    // Listar todas con filtros opcionales
    @GetMapping
    public ResponseEntity<List<ConcesionariaDTO>> listar(
            @RequestParam(name = "paisId", required = false) Long paisId,
            @RequestParam(name = "provinciaId", required = false) Long provinciaId,
            @RequestParam(name = "localidadId", required = false) Long localidadId,
            @RequestParam(name = "nombre", required = false) String nombre) {
        return ResponseEntity.ok(service.listar(paisId, provinciaId, localidadId, nombre));
    }

    // Obtener una sola por ID
    @GetMapping("/{id}")
    public ResponseEntity<ConcesionariaDTO> findById(@PathVariable("id") Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear nueva
    @PostMapping
    public ResponseEntity<ConcesionariaDTO> crear(@RequestBody @Valid ConcesionariaDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    // Actualizar una por ID
    @PutMapping("/{id}")
    public ResponseEntity<ConcesionariaDTO> actualizar(@PathVariable("id") Long id,
                                                       @RequestBody @Valid ConcesionariaDTO dto) {
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}