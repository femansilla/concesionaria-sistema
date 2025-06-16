package com.concesionaria.controller;

import com.concesionaria.model.Pais;
import com.concesionaria.repository.PaisRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paises")
public class PaisController {

    private final PaisRepository repository;

    public PaisController(PaisRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Pais> findAll() {
        return repository.findAll();
    }

    @PostMapping
    public Pais save(@RequestBody Pais pais) {
        return repository.save(pais);
    }
}