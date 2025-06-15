package com.vehiculos.controller;

import com.vehiculos.model.Stock;
import com.vehiculos.repository.StockRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {

    private final StockRepository repository;

    public StockController(StockRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Stock> findAll() {
        return repository.findAll();
    }

    @GetMapping("/concesionaria/{id}")
    public List<Stock> findByConcesionaria(@PathVariable Long id) {
        return repository.findByConcesionariaId(id);
    }

    @PostMapping
    public Stock save(@RequestBody Stock stock) {
        return repository.save(stock);
    }
}
