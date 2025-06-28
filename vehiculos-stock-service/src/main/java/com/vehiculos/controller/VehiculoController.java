package com.vehiculos.controller;

import com.vehiculos.DTO.VehiculoDTO;
import com.vehiculos.model.TipoVehiculo;
import com.vehiculos.model.Vehiculo;
import com.vehiculos.repository.TipoVehiculoRepository;
import com.vehiculos.repository.VehiculoRepository;
import com.vehiculos.service.VehiculoService;

import jakarta.persistence.criteria.Predicate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoService service;

    @GetMapping
    public List<VehiculoDTO> findAllByFilter(
            @RequestParam(name = "marca", required = false) String marca,
            @RequestParam(name = "modelo", required = false) String modelo,
            @RequestParam(name = "tipoId", required = false) Long tipoId,
            @RequestParam(name = "precioMaximo", required = false) BigDecimal precioMax,
            @RequestParam(name = "precioMinimo", required = false) BigDecimal precioMin
    ) {
        return service.findAllByFilter(marca, modelo, tipoId, precioMin, precioMax);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculoDTO> findById(@PathVariable("id") Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public VehiculoDTO save(@RequestBody @Valid VehiculoDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculoDTO> update(@PathVariable("id") Long id, @RequestBody @Valid VehiculoDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}