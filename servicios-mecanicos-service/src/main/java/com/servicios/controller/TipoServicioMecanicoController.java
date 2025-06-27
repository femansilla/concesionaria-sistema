package com.servicios.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servicios.DTO.TipoServicioMecanicoDTO;
import com.servicios.model.TipoServicioMecanico;
import com.servicios.repository.TipoServicioMecanicoRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("servicios/tipos")
@RequiredArgsConstructor
public class TipoServicioMecanicoController {

    private final TipoServicioMecanicoRepository repository;
    private final ModelMapper mapper;

    @GetMapping
    public List<TipoServicioMecanicoDTO> findAll(@RequestParam(name = "descripcion", required = false) String descripcion) {
        return repository.buscarPorDescripcion(descripcion)
                .stream().map(t -> mapper.map(t, TipoServicioMecanicoDTO.class)).toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody TipoServicioMecanicoDTO dto) {
        return repository.findById(id).map(entidad -> {
            entidad.setDescripcion(dto.getDescripcion());
            entidad.setPrecio(dto.getPrecio());
            entidad.setCantDiasServicio(dto.getCantDiasServicio());
            return ResponseEntity.ok(mapper.map(repository.save(entidad), TipoServicioMecanicoDTO.class));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TipoServicioMecanicoDTO save(@RequestBody @Valid TipoServicioMecanicoDTO dto) {
        TipoServicioMecanico entidad = mapper.map(dto, TipoServicioMecanico.class);
        return mapper.map(repository.save(entidad), TipoServicioMecanicoDTO.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoServicioMecanicoDTO> findById(@PathVariable("id") Long id) {
        return repository.findById(id)
                .map(e -> mapper.map(e, TipoServicioMecanicoDTO.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
