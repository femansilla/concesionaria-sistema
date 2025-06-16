package com.servicios.controller;

import com.servicios.model.ServicioMecanico;
import com.servicios.repository.ServicioMecanicoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicios")
public class ServicioMecanicoController {

    private final ServicioMecanicoRepository repository;

    public ServicioMecanicoController(ServicioMecanicoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ServicioMecanico> findAll() {
        return repository.findAll();
    }

    @PostMapping
    public ServicioMecanico save(@RequestBody ServicioMecanico servicio) {
        return repository.save(servicio);
    }

    @GetMapping("/{id}")
    public ServicioMecanico findById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }
}