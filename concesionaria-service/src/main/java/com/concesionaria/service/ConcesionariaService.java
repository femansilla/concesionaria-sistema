package com.concesionaria.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.concesionaria.DTO.ConcesionariaDTO;
import com.concesionaria.repository.ConcesionariaRepository;
import com.concesionaria.repository.LocalidadRepository;
import com.concesionaria.repository.PaisRepository;
import com.concesionaria.repository.ProvinciaRepository;
import com.concesionaria.model.Concesionaria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcesionariaService {
    private final ConcesionariaRepository repository;
    private final PaisRepository paisRepository;
    private final ProvinciaRepository provinciaRepository;
    private final LocalidadRepository localidadRepository;
    private final ModelMapper mapper;

    public ConcesionariaDTO save(ConcesionariaDTO dto) {
        Concesionaria entity = mapearEntidad(dto);
        return mapper.map(repository.save(entity), ConcesionariaDTO.class);
    }

    public List<ConcesionariaDTO> listar(Long paisId, Long provinciaId, Long localidadId, String nombre) {
        return repository.buscarPorFiltros(paisId, provinciaId, localidadId, nombre)
                .stream()
                .map(this::mapearDTO)
                .toList();
    }

    public Optional<ConcesionariaDTO> findById(Long id) {
        return repository.findById(id).map(this::mapearDTO);
    }
    
    public ConcesionariaDTO update(Long id, ConcesionariaDTO dto) {
        Concesionaria existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concesionaria no encontrada"));

        existente.setNombre(dto.getNombre());
        existente.setDireccion(dto.getDireccion());
        existente.setFechaApertura(dto.getFechaApertura());
        existente.setPais(paisRepository.findById(dto.getPaisId())
                .orElseThrow(() -> new RuntimeException("País no encontrado")));
        existente.setProvincia(provinciaRepository.findById(dto.getProvinciaId())
                .orElseThrow(() -> new RuntimeException("Provincia no encontrada")));
        existente.setLocalidad(localidadRepository.findById(dto.getLocalidadId())
                .orElseThrow(() -> new RuntimeException("Localidad no encontrada")));

        return mapearDTO(repository.save(existente));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private ConcesionariaDTO mapearDTO(Concesionaria c) {
        ConcesionariaDTO dto = mapper.map(c, ConcesionariaDTO.class);
        dto.setPaisId(c.getPais().getId());
        dto.setProvinciaId(c.getProvincia().getId());
        dto.setLocalidadId(c.getLocalidad().getId());
        return dto;
    }

    private Concesionaria mapearEntidad(ConcesionariaDTO dto) {
        Concesionaria entity = mapper.map(dto, Concesionaria.class);
        entity.setPais(paisRepository.findById(dto.getPaisId())
                .orElseThrow(() -> new RuntimeException("País no encontrado")));
        entity.setProvincia(provinciaRepository.findById(dto.getProvinciaId())
                .orElseThrow(() -> new RuntimeException("Provincia no encontrada")));
        entity.setLocalidad(localidadRepository.findById(dto.getLocalidadId())
                .orElseThrow(() -> new RuntimeException("Localidad no encontrada")));
        return entity;
    }

}
