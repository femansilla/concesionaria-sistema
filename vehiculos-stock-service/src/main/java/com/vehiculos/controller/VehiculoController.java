package com.vehiculos.controller;

import com.vehiculos.model.TipoVehiculo;
import com.vehiculos.model.Vehiculo;
import com.vehiculos.repository.TipoVehiculoRepository;
import com.vehiculos.repository.VehiculoRepository;

import jakarta.persistence.criteria.Predicate;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

    private final VehiculoRepository repository;
    private final TipoVehiculoRepository tipoVehiculoRepository;

    public VehiculoController(VehiculoRepository repository,
                          TipoVehiculoRepository tipoVehiculoRepository) {
        this.repository = repository;
        this.tipoVehiculoRepository = tipoVehiculoRepository;
    }

    @GetMapping
    public List<Vehiculo> findAllByFilter(
        @RequestParam(name = "marca", required = false) String marca,
        @RequestParam(name = "modelo", required = false) String modelo,
        @RequestParam(name = "tipoId", required = false) String tipoId,
        @RequestParam(name = "precioMaximo", required = false) BigDecimal precioMaximo,
        @RequestParam(name = "precioMinimo", required = false) BigDecimal precioMinimo
    ) {
        return repository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (marca != null) predicates.add(cb.equal(root.get("marca"), marca));
            if (modelo != null) predicates.add(cb.equal(root.get("modelo"), modelo));
            if (tipoId != null) predicates.add(cb.equal(root.get("tipoVehiculo").get("id"), tipoId));
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
    public Vehiculo save(@RequestBody @Valid Vehiculo vehiculo) {
        return repository.save(vehiculo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable("id") Long id, @RequestBody @Valid Vehiculo vehiculoActualizado) {
        return repository.findById(id).map(vehiculoExistente -> {
            // Validación de existencia del tipo de vehículo
            Long tipoVehiculoId = vehiculoActualizado.getTipoVehiculo().getId();
            TipoVehiculo tipo = tipoVehiculoRepository.findById(tipoVehiculoId)
                    .orElseThrow(() -> new RuntimeException("Tipo de vehículo no válido"));

            // Actualizamos campos
            vehiculoExistente.setMarca(vehiculoActualizado.getMarca());
            vehiculoExistente.setModelo(vehiculoActualizado.getModelo());
            vehiculoExistente.setAnio(vehiculoActualizado.getAnio());
            vehiculoExistente.setPrecioUnidad(vehiculoActualizado.getPrecioUnidad());
            vehiculoExistente.setTipoVehiculo(tipo);

            repository.save(vehiculoExistente);
            return ResponseEntity.ok(vehiculoExistente);
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