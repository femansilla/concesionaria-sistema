package com.clientes.controller;

import com.clientes.model.Cliente;
import com.clientes.repository.ClienteRepository;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository repository;

    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Cliente> findAll(
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "apellido", required = false) String apellido,
            @RequestParam(name = "dni", required = false) String dni) {

        return repository.findAll().stream()
                .filter(c -> nombre == null || c.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .filter(c -> apellido == null || c.getApellido().toLowerCase().contains(apellido.toLowerCase()))
                .filter(c -> dni == null || c.getDni().equalsIgnoreCase(dni))
                .toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody Cliente clienteActualizado) {
        return repository.findById(id).map(clienteExistente -> {
            clienteExistente.setNombre(clienteActualizado.getNombre());
            clienteExistente.setApellido(clienteActualizado.getApellido());
            clienteExistente.setDni(clienteActualizado.getDni());
            clienteExistente.setEmail(clienteActualizado.getEmail());
            clienteExistente.setTelefono(clienteActualizado.getTelefono());
            return ResponseEntity.ok(repository.save(clienteExistente));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cliente save(@RequestBody @Valid Cliente cliente) {
        return repository.save(cliente);
    }

    @GetMapping("/{id}")
    public Cliente findById(@PathVariable("id") Long id) {
        return repository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Long id) {
        return repository.findById(id)
            .map(c -> {
                repository.deleteById(id);
                return ResponseEntity.noContent().build();
            }).orElse(ResponseEntity.notFound().build());
    }
}
