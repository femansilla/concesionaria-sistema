package com.vehiculos.controller;

import com.vehiculos.model.Vehiculo;
import com.vehiculos.repository.VehiculoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

    private final VehiculoRepository repository;

    public VehiculoController(VehiculoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Vehiculo> findAll() {
        return repository.findAll();
    }

    @PostMapping
    public Vehiculo save(@RequestBody Vehiculo vehiculo) {
        return repository.save(vehiculo);
    }
}