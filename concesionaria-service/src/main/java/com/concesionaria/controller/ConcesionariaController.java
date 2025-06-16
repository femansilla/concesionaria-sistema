package com.concesionaria.controller;

import com.concesionaria.model.Concesionaria;
import com.concesionaria.repository.ConcesionariaRepository;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/concesionarias")
public class ConcesionariaController {

    private final ConcesionariaRepository repository;

    public ConcesionariaController(ConcesionariaRepository repository) {
        this.repository = repository;
    }

    // Listar todas con filtros opcionales
    @GetMapping
    public List<Concesionaria> listar(
        @RequestParam(name = "paisId", required = false) Long paisId,
        @RequestParam(name = "provinciaId", required = false) Long provinciaId,
        @RequestParam(name = "localidadId", required = false) Long localidadId,
        @RequestParam(name = "nombre", required = false) String nombre
    ) {
        return repository.findAll().stream()
            .filter(c -> paisId == null || (c.getPais() != null && c.getPais().getId().equals(paisId)))
            .filter(c -> provinciaId == null || 
                         (c.getLocalidad() != null && 
                          c.getLocalidad().getProvincia() != null &&
                          c.getLocalidad().getProvincia().getId().equals(provinciaId)))
            .filter(c -> localidadId == null || (c.getLocalidad() != null && c.getLocalidad().getId().equals(localidadId)))
            .filter(c -> nombre == null || c.getNombre().toLowerCase().contains(nombre.toLowerCase()))
            .collect(Collectors.toList());
    }

    // Obtener una sola por ID
    @GetMapping("/{id}")
    public ResponseEntity<Concesionaria> findById(@PathVariable("id") Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Crear nueva
    @PostMapping
    public ResponseEntity<?> crearConcesionaria(@Valid @RequestBody Concesionaria concesionaria) {
        return ResponseEntity.ok(repository.save(concesionaria));
    }

    // Actualizar una por ID
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarConcesionaria(@PathVariable("id") Long id, @Valid @RequestBody Concesionaria actualizada) {
        return repository.findById(id)
            .map(c -> {
                c.setNombre(actualizada.getNombre());
                c.setDireccion(actualizada.getDireccion());
                c.setFechaApertura(actualizada.getFechaApertura());
                c.setPais(actualizada.getPais());
                c.setLocalidad(actualizada.getLocalidad());
                return ResponseEntity.ok(repository.save(c));
            }).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarConcesionaria(@PathVariable("id") Long id) {
        return repository.findById(id)
            .map(c -> {
                repository.deleteById(id);
                return ResponseEntity.noContent().build();
            }).orElse(ResponseEntity.notFound().build());
    }
}