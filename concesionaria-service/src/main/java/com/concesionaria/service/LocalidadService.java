package com.concesionaria.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.concesionaria.DTO.LocalidadDTO;
import com.concesionaria.repository.ProvinciaRepository;
import com.concesionaria.repository.LocalidadRepository;
import com.concesionaria.model.Localidad;
import com.concesionaria.model.Provincia;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocalidadService {
    
    private final LocalidadRepository repository;
    private final ProvinciaRepository provinciaRepository;
    private final ModelMapper mapper;

    public LocalidadDTO save(LocalidadDTO dto) {
        Localidad entidad = mapper.map(dto, Localidad.class);
        entidad.setProvincia(provinciaRepository.findById(dto.getProvinciaId())
                .orElseThrow(() -> new RuntimeException("Provincia no encontrada")));
        return mapearDTO(repository.save(entidad));
    }

    public List<LocalidadDTO> findAll(Long provinciaId, String nombre) {
        if (provinciaId == null && nombre == null) {
            return repository.findAll().stream()
                    .map(this::mapearDTO).toList();
        }
        return repository.buscarPorFiltros(provinciaId, nombre).stream()
                .map(this::mapearDTO).toList();
    }

    public Optional<LocalidadDTO> findById(Long id) {
        return repository.findById(id).map(this::mapearDTO);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private LocalidadDTO mapearDTO(Localidad l) {
        LocalidadDTO dto = mapper.map(l, LocalidadDTO.class);
        dto.setProvinciaId(l.getProvincia().getId());
        return dto;
    }
}
