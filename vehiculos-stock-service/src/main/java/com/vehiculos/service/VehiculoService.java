package com.vehiculos.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.vehiculos.DTO.VehiculoDTO;
import com.vehiculos.model.Vehiculo;
import com.vehiculos.repository.TipoVehiculoRepository;
import com.vehiculos.repository.VehiculoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehiculoService {
    private final VehiculoRepository repository;
    private final TipoVehiculoRepository tipoVehiculoRepository;
    private final ModelMapper mapper;

    public VehiculoDTO save(VehiculoDTO dto) {
        Vehiculo entidad = mapper.map(dto, Vehiculo.class);
        entidad.setTipoVehiculo(tipoVehiculoRepository.findById(dto.getTipoVehiculoId())
                .orElseThrow(() -> new RuntimeException("TipoVehiculo no encontrado")));
        return mapearDTO(repository.save(entidad));
    }

    public List<VehiculoDTO> findAllByFilter(String marca, String modelo, Long tipoId, BigDecimal precioMin, BigDecimal precioMax) {
        return repository.buscarPorFiltros(marca, modelo, tipoId, precioMin, precioMax).stream()
                .map(this::mapearDTO).toList();
    }

    public Optional<VehiculoDTO> findById(Long id) {
        return repository.findById(id).map(this::mapearDTO);
    }

    public VehiculoDTO update(Long id, VehiculoDTO dto) {
        Vehiculo vehiculo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado"));

        vehiculo.setMarca(dto.getMarca());
        vehiculo.setModelo(dto.getModelo());
        vehiculo.setAnio(dto.getAnio());
        vehiculo.setPrecioUnidad(dto.getPrecioUnidad());
        vehiculo.setTipoVehiculo(tipoVehiculoRepository.findById(dto.getTipoVehiculoId())
                .orElseThrow(() -> new RuntimeException("TipoVehiculo no encontrado")));

        return mapearDTO(repository.save(vehiculo));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private VehiculoDTO mapearDTO(Vehiculo v) {
        VehiculoDTO dto = mapper.map(v, VehiculoDTO.class);
        dto.setTipoVehiculoId(v.getTipoVehiculo().getId());
        return dto;
    }

    
}
