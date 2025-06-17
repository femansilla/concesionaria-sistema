package com.concesionaria.controller;

import com.concesionaria.model.Localidad;
import com.concesionaria.model.Pais;
import com.concesionaria.repository.PaisRepository;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paises")
public class PaisController {

    private final PaisRepository repository;

    public PaisController(PaisRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Pais> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pais> findById(@PathVariable("id") Long id) {
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
    public Pais save(@RequestBody @Valid Pais pais) {
        return repository.save(pais);
    }
}