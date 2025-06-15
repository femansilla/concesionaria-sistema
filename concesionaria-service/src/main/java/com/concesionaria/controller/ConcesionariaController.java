package main.java.com.concesionaria.controller;

import com.concesionaria.model.Concesionaria;
import com.concesionaria.repository.ConcesionariaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concesionarias")
public class ConcesionariaController {

    private final ConcesionariaRepository repository;

    public ConcesionariaController(ConcesionariaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Concesionaria> findAll() {
        return repository.findAll();
    }

    @PostMapping
    public Concesionaria save(@RequestBody Concesionaria concesionaria) {
        return repository.save(concesionaria);
    }
}