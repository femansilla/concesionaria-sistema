package main.java.com.ventas.controller;

import com.ventas.model.Venta;
import com.ventas.repository.VentaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final VentaRepository repository;

    public VentaController(VentaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Venta> findAll() {
        return repository.findAll();
    }

    @PostMapping
    public Venta save(@RequestBody Venta venta) {
        return repository.save(venta);
    }

    @GetMapping("/{id}")
    public Venta findById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }
}
