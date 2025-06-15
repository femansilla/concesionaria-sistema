package main.java.com.concesionaria.controller;

import com.concesionaria.model.Localidad;
import com.concesionaria.repository.LocalidadRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/localidades")
public class LocalidadController {

    private final LocalidadRepository repository;

    public LocalidadController(LocalidadRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Localidad> findAll() {
        return repository.findAll();
    }

    @PostMapping
    public Localidad save(@RequestBody Localidad localidad) {
        return repository.save(localidad);
    }
}