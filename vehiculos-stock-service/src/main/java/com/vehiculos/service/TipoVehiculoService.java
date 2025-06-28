package com.vehiculos.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.vehiculos.DTO.TipoVehiculoDTO;
import com.vehiculos.model.TipoVehiculo;
import com.vehiculos.repository.TipoVehiculoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoVehiculoService {
    private final TipoVehiculoRepository repository;
    private final ModelMapper mapper;

    public TipoVehiculoDTO save(TipoVehiculoDTO dto) {
        TipoVehiculo entidad = mapper.map(dto, TipoVehiculo.class);
        return mapper.map(repository.save(entidad), TipoVehiculoDTO.class);
    }

    public List<TipoVehiculoDTO> buscarPorFiltros(String descripcion, Integer aniosGarantia, Integer kilometrosGarantia) {
        return repository.buscarPorFiltros(descripcion, aniosGarantia, kilometrosGarantia).stream()
                .map(t -> mapper.map(t, TipoVehiculoDTO.class))
                .toList();
    }

    public TipoVehiculoDTO update(Long id, TipoVehiculoDTO dto) {
        TipoVehiculo existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoVehiculo no encontrado"));

        existente.setDescripcion(dto.getDescripcion());
        existente.setGarantiaAnios(dto.getGarantiaAnios());
        existente.setGarantiaKilometros(dto.getGarantiaKilometros());
        existente.setAdicionalServicio(dto.getAdicionalServicio());

        return mapper.map(repository.save(existente), TipoVehiculoDTO.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Optional<TipoVehiculoDTO> findById(Long id) {
        return repository.findById(id).map(t -> mapper.map(t, TipoVehiculoDTO.class));
    }
}
