package com.concesionaria.controller;

import com.concesionaria.DTO.LocalidadDTO;
import com.concesionaria.model.Localidad;
import com.concesionaria.repository.LocalidadRepository;
import com.concesionaria.service.LocalidadService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concesionarias/localidades")
@RequiredArgsConstructor
public class LocalidadController {

    private final LocalidadService service;

    @GetMapping
    public List<LocalidadDTO> findAll(@RequestParam(name = "provinciaId", required = false) Long provinciaId,
                                      @RequestParam(name = "nombre", required = false) String nombre) {
        return service.findAll(provinciaId, nombre);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalidadDTO> findById(@PathVariable("id") Long id) {
        return service.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public LocalidadDTO save(@RequestBody @Valid LocalidadDTO dto) {
        return service.save(dto);
    }
}