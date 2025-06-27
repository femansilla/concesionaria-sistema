package com.concesionaria.controller;

import com.concesionaria.DTO.PaisDTO;
import com.concesionaria.model.Pais;
import com.concesionaria.service.PaisService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concesionarias/paises")
@RequiredArgsConstructor
public class PaisController {

    private final PaisService service;

    @GetMapping
    public List<PaisDTO> findAll(@RequestParam(name = "nombre", required = false) String nombre) {
        return service.findAll(nombre);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaisDTO> findById(@PathVariable("id") Long id) {
        return service.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public PaisDTO save(@RequestBody @Valid PaisDTO dto) {
        return service.save(dto);
    }
}