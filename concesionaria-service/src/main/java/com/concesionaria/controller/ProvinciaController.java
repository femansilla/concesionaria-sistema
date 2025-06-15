package main.java.com.concesionaria.controller;

import com.concesionaria.model.Provincia;
import com.concesionaria.repository.ProvinciaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/provincias")
public class ProvinciaController {

    private final ProvinciaRepository repository;

    public ProvinciaController(ProvinciaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Provincia> findAll() {
        return repository.findAll();
    }

    @PostMapping
    public Provincia save(@RequestBody Provincia provincia) {
        return repository.save(provincia);
    }
}