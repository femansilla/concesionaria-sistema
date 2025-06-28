package com.vehiculos.controller;
import com.vehiculos.DTO.StockDTO;
import com.vehiculos.service.StockService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehiculos/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService service;

    // 1. Stock general (tal cual tabla)
    @GetMapping
    public List<StockDTO> findAll() {
        return service.findAll();
    }

    // 2. Stock de una concesionaria
    @GetMapping("/concesionaria/{id}")
    public List<StockDTO> findByConcesionaria(@PathVariable("id") Long id) {
        return service.findByConcesionaria(id);
    }

    // 3. Stock de un vehículo general (sin filtro)
    @GetMapping("/vehiculo/{vehiculoId}")
    public ResponseEntity<?> getStockVehiculo(
        @PathVariable("vehiculoId") Long vehiculoId,
        @RequestParam(name = "concesionariaId", required = false) Long concesionariaId) {
        return ResponseEntity.ok(service.obtenerDisponibilidad(vehiculoId, concesionariaId));
    }
    // 4. Crear stock
    @PostMapping
    public StockDTO save(@RequestBody @Valid StockDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/descontar")
    public ResponseEntity<Void> descontar(@RequestParam("vehiculoId") Long vehiculoId,
                                          @RequestParam("cantidad") Integer cantidad) {
        service.descontarStock(vehiculoId, cantidad);
        return ResponseEntity.ok().build();
    }
}
