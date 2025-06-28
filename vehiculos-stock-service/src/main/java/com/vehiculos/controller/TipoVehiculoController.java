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

import com.vehiculos.DTO.TipoVehiculoDTO;
import com.vehiculos.model.TipoVehiculo;
import com.vehiculos.repository.TipoVehiculoRepository;
import com.vehiculos.service.TipoVehiculoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vehiculos/tipos")
@RequiredArgsConstructor
public class TipoVehiculoController {

    private final TipoVehiculoService service;

    @GetMapping
    public List<TipoVehiculoDTO> findAll(
            @RequestParam(name = "descripcion", required = false) String descripcion,
            @RequestParam(name = "aniosGarantia", required = false) Integer aniosGarantia,
            @RequestParam(name = "kilometrosGarantia", required = false) Integer kilometrosGarantia) {
        return service.buscarPorFiltros(descripcion, aniosGarantia, kilometrosGarantia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoVehiculoDTO> update(@PathVariable("id") Long id, @RequestBody @Valid TipoVehiculoDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PostMapping
    public TipoVehiculoDTO save(@RequestBody @Valid TipoVehiculoDTO dto) {
        return service.save(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoVehiculoDTO> findById(@PathVariable("id") Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
