package com.concesionaria.controller;

import com.concesionaria.model.Localidad;
import com.concesionaria.model.Provincia;
import com.concesionaria.repository.ProvinciaRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/provincias")
public class ProvinciaController {

    private final ProvinciaRepository repository;

    public ProvinciaController(ProvinciaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Provincia> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Provincia> findById(@PathVariable("id") Long id) {
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
    public Provincia save(@RequestBody Provincia provincia) {
        return repository.save(provincia);
    }
}