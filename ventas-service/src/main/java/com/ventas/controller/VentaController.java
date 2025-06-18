package com.ventas.controller;

import com.ventas.model.Venta;
import com.ventas.model.VentaDTO;
import com.ventas.model.VentaRequest;
import com.ventas.repository.VentaRepository;
import com.ventas.service.VentaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public List<VentaDTO> findAll() {
        return ventaService.findAll();
    }

    @PostMapping
    public Venta save(@RequestBody VentaRequest venta) {
        return ventaService.save(venta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> findById(@PathVariable("id") Long id) {
        return ventaService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
