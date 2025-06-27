package com.concesionaria.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.concesionaria.DTO.ProvinciaDTO;
import com.concesionaria.repository.PaisRepository;
import com.concesionaria.repository.ProvinciaRepository;
import com.concesionaria.model.Provincia;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProvinciaService {
    
    private final ProvinciaRepository repository;
    private final PaisRepository paisRepository;
    private final ModelMapper mapper;

    public ProvinciaDTO save(ProvinciaDTO dto) {
        Provincia entidad = mapper.map(dto, Provincia.class);
        entidad.setPais(paisRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Pais no encontrado")));
        return mapearDTO(repository.save(entidad));
    }

    public List<ProvinciaDTO> findAll(Long paisId, String nombre) {
        if (paisId == null && nombre == null) {
            return repository.findAll().stream()
                    .map(this::mapearDTO).toList();
        }
        return repository.buscarPorFiltros(paisId, nombre).stream()
                .map(this::mapearDTO).toList();
    }

    public Optional<ProvinciaDTO> findById(Long id) {
        return repository.findById(id).map(this::mapearDTO);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private ProvinciaDTO mapearDTO(Provincia p) {
        ProvinciaDTO dto = mapper.map(p, ProvinciaDTO.class);
        dto.setPaisId(p.getPais().getId());
        return dto;
    }
}
