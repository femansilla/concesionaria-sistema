package com.servicios.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servicios.model.TipoServicioMecanico;
import com.servicios.repository.TipoServicioMecanicoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("servicios/tipos")
public class TipoServicioMecanicoController {

    private final TipoServicioMecanicoRepository repository;

    public TipoServicioMecanicoController(TipoServicioMecanicoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<TipoServicioMecanico> findAll(
            @RequestParam(name = "descripcion", required = false) String descripcion) {

        return repository.findAll().stream()
                .filter(c -> descripcion == null || c.getDescripcion().toLowerCase().contains(descripcion.toLowerCase()))
                .toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody TipoServicioMecanico TipoServicioMecanicoActualizado) {
        return repository.findById(id).map(TipoServicioMecanicoExistente -> {
            TipoServicioMecanicoExistente.setDescripcion(TipoServicioMecanicoActualizado.getDescripcion());
            return ResponseEntity.ok(repository.save(TipoServicioMecanicoExistente));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TipoServicioMecanico save(@RequestBody @Valid TipoServicioMecanico TipoServicioMecanico) {
        return repository.save(TipoServicioMecanico);
    }

    @GetMapping("/{id}")
    public TipoServicioMecanico findById(@PathVariable("id") Long id) {
        return repository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Long id) {
        return repository.findById(id)
            .map(c -> {
                repository.deleteById(id);
                return ResponseEntity.noContent().build();
            }).orElse(ResponseEntity.notFound().build());
    }
}
