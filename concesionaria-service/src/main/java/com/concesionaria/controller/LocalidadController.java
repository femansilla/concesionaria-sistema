package com.concesionaria.controller;

import com.concesionaria.model.Concesionaria;
import com.concesionaria.model.Localidad;
import com.concesionaria.repository.LocalidadRepository;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/localidades")
public class LocalidadController {

    private final LocalidadRepository repository;

    public LocalidadController(LocalidadRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Localidad> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Localidad> findById(@PathVariable("id") Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Long id) {
        return repository.findById(id)
            .map(c -> {
                repository.deleteById(id);
                return ResponseEntity.noContent().build();
            }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Localidad save(@RequestBody Localidad localidad) {
        return repository.save(localidad);
    }
}