package com.vehiculos.controller;

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

import com.vehiculos.model.TipoVehiculo;
import com.vehiculos.repository.TipoVehiculoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tipos")
public class TipoVehiculoController {

    private final TipoVehiculoRepository repository;

    public TipoVehiculoController(TipoVehiculoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<TipoVehiculo> findAll(
            @RequestParam(name = "descripcion", required = false) String descripcion,
            @RequestParam(name = "aniosGarantia", required = false) String aniosGarantia,
            @RequestParam(name = "kilometrosGarantia", required = false) String kilometrosGarantia) {

        return repository.findAll().stream()
                .filter(c -> descripcion == null || c.getDescripcion().toLowerCase().contains(descripcion.toLowerCase()))
                .filter(c -> aniosGarantia == null || c.getGarantiaAnios().equals(aniosGarantia))
                .filter(c -> kilometrosGarantia == null || c.getGarantiaKilometros().equals(kilometrosGarantia))
                .toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody TipoVehiculo TipoVehiculoActualizado) {
        return repository.findById(id).map(TipoVehiculoExistente -> {
            TipoVehiculoExistente.setDescripcion(TipoVehiculoActualizado.getDescripcion());
            TipoVehiculoExistente.setGarantiaAnios(TipoVehiculoActualizado.getGarantiaAnios());
            TipoVehiculoExistente.setGarantiaKilometros(TipoVehiculoActualizado.getGarantiaKilometros());
            return ResponseEntity.ok(repository.save(TipoVehiculoExistente));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TipoVehiculo save(@RequestBody @Valid TipoVehiculo TipoVehiculo) {
        return repository.save(TipoVehiculo);
    }

    @GetMapping("/{id}")
    public TipoVehiculo findById(@PathVariable("id") Long id) {
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
