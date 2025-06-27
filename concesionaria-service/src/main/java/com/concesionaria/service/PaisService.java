package com.concesionaria.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.concesionaria.DTO.PaisDTO;
import com.concesionaria.model.Pais;
import com.concesionaria.repository.PaisRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaisService {
    private final PaisRepository repository;
    private final ModelMapper mapper;

    public PaisDTO save(PaisDTO dto) {
        return mapper.map(repository.save(mapper.map(dto, Pais.class)), PaisDTO.class);
    }

    public List<PaisDTO> findAll(String nombre) {
        if (nombre == null) {
            return repository.findAll().stream()
                    .map(p -> mapper.map(p, PaisDTO.class)).toList();
        }
        return repository.findByNombreContainingIgnoreCase(nombre)
                .stream().map(p -> mapper.map(p, PaisDTO.class)).toList();
    }

    public Optional<PaisDTO> findById(Long id) {
        return repository.findById(id).map(p -> mapper.map(p, PaisDTO.class));
    }

    public PaisDTO update(Long id, PaisDTO dto) {
        Pais entidad = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pais no encontrado"));
        entidad.setNombre(dto.getNombre());
        return mapper.map(repository.save(entidad), PaisDTO.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    
}
