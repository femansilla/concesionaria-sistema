package com.concesionaria.controller;

import com.concesionaria.DTO.ProvinciaDTO;
import com.concesionaria.model.Provincia;
import com.concesionaria.repository.ProvinciaRepository;
import com.concesionaria.service.ProvinciaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concesionarias/provincias")
@RequiredArgsConstructor
public class ProvinciaController {

    private final ProvinciaService service;

    @GetMapping
    public List<ProvinciaDTO> findAll(@RequestParam(name = "paisId", required = false) Long paisId,
                                      @RequestParam(name = "nombre", required = false) String nombre) {
        return service.findAll(paisId, nombre);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProvinciaDTO> findById(@PathVariable("id") Long id) {
        return service.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ProvinciaDTO save(@RequestBody @Valid ProvinciaDTO dto) {
        return service.save(dto);
    }
}