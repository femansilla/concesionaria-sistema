package com.vehiculos.controller;

import com.vehiculos.model.Vehiculo;
import com.vehiculos.repository.VehiculoRepository;

import jakarta.persistence.criteria.Predicate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

    private final VehiculoRepository repository;

    public VehiculoController(VehiculoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Vehiculo> findAllByFilter(
        @RequestParam(name = "marca", required = false) String marca,
        @RequestParam(name = "modelo", required = false) String modelo,
        @RequestParam(name = "tipo", required = false) String tipo,
        @RequestParam(name = "precioMaximo", required = false) BigDecimal precioMaximo,
        @RequestParam(name = "precioMinimo", required = false) BigDecimal precioMinimo
    ) {
        return repository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (marca != null) predicates.add(cb.equal(root.get("marca"), marca));
            if (modelo != null) predicates.add(cb.equal(root.get("modelo"), modelo));
            if (tipo != null) predicates.add(cb.equal(root.get("tipo"), tipo));
            if (precioMinimo != null) predicates.add(cb.greaterThanOrEqualTo(root.get("precioUnidad"), precioMinimo));
            if (precioMaximo != null) predicates.add(cb.lessThanOrEqualTo(root.get("precioUnidad"), precioMaximo));

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> findById(@PathVariable("id") Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Vehiculo save(@RequestBody Vehiculo vehiculo) {
        return repository.save(vehiculo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> update(@PathVariable("id") Long id, @RequestBody Vehiculo datos) {
        return repository.findById(id).map(v -> {
            v.setMarca(datos.getMarca());
            v.setModelo(datos.getModelo());
            v.setTipo(datos.getTipo());
            v.setAnio(datos.getAnio());
            v.setGarantiaAnios(datos.getGarantiaAnios());
            v.setGarantiaKilometros(datos.getGarantiaKilometros());
            v.setPrecioUnidad(datos.getPrecioUnidad());
            return ResponseEntity.ok(repository.save(v));
        }).orElse(ResponseEntity.notFound().build());
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