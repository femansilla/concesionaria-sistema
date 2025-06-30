package com.ventas.controller;

import com.ventas.DTO.VentaDTO;
import com.ventas.model.Venta;
import com.ventas.model.VentaRequest;
import com.ventas.model.VentaResponse;
import com.ventas.service.VentaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public List<VentaResponse> findAll() {
        return ventaService.findAll();
    }

    @PostMapping
    public ResponseEntity<VentaResponse> save(@RequestBody @Valid VentaRequest ventaRequest) {
        VentaResponse venta = ventaService.save(ventaRequest);
        return ResponseEntity.ok(venta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse> findById(@PathVariable("id") Long id) {
        return ventaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
