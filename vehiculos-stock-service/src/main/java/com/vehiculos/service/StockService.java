package com.vehiculos.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.vehiculos.DTO.ConcesionariaDTO;
import com.vehiculos.DTO.StockDTO;
import com.vehiculos.client.ConcesionariaFeignClient;
import com.vehiculos.model.Stock;
import com.vehiculos.repository.StockRepository;
import com.vehiculos.repository.VehiculoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository repository;
    private final VehiculoRepository vehiculoRepository;
    private final ConcesionariaFeignClient concesionariaClient;
    private final ModelMapper mapper;

    public List<StockDTO> findAll() {
        return repository.findAll().stream()
                .map(this::mapearDTO).toList();
    }

    public Optional<Stock> findById(Long id) {
        return repository.findById(id);
    }

    public List<StockDTO> findByConcesionaria(Long concesionariaId) {
        return repository.findByConcesionariaId(concesionariaId).stream()
                .map(this::mapearDTO).toList();
    }
    
    public Map<String, Object> obtenerDisponibilidad(Long vehiculoId, Long concesionariaId) {
        // Validación de existencia
        vehiculoRepository.findById(vehiculoId)
            .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        // Si viene la concesionaria
        if (concesionariaId != null) {
            List<Stock> stockList = repository.findByVehiculoIdAndConcesionariaId(vehiculoId, concesionariaId);

            if (!stockList.isEmpty() && stockList.get(0).getCantidad() > 0) {
                Stock stock = stockList.get(0);
                return Map.of(
                        "disponibleEn", "concesionaria",
                        "stock", stock.getCantidad(),
                        "tiempoEntregaDias", stock.getTiempoEntregaDias()
                );
            }

            // Si no hay stock local, consultar stock central
            List<Stock> stockCentral = repository.findByVehiculoIdAndConcesionariaIdIsNull(vehiculoId);

            if (!stockCentral.isEmpty() && stockCentral.get(0).getCantidad() > 0) {
                Stock central = stockCentral.get(0);

                ConcesionariaDTO dto = concesionariaClient.obtenerConcesionaria(concesionariaId);

                int demoraExtra;
                switch (dto.getLocalidadId().intValue()) {
                    case 1:
                        demoraExtra = 7;
                        break;
                    case 2:
                        demoraExtra = 10;
                        break;
                    case 4:
                        demoraExtra = 14;
                        break;
                    default:
                        demoraExtra = 20;
                        break;
                }

                int demoraTotal = central.getTiempoDesdeCentralDias() + demoraExtra;

                return Map.of(
                        "disponibleEn", "stock central",
                        "stock", central.getCantidad(),
                        "tiempoEntregaTotal", demoraTotal
                );
            }

            return Map.of(
                    "disponibleEn", "sin stock",
                    "stock", 0
            );
        }

        // Si no se pasa concesionariaId, mostrar stock total
        Optional<Stock> todosStock = repository.findByVehiculoId(vehiculoId);
        if (todosStock.isPresent()) {
            Stock stock = todosStock.get();
            return Map.of(
                    "disponibleEn", "general",
                    "stock", stock.getCantidad(),
                    "tiempoEntregaDias", stock.getTiempoEntregaDias()
            );
        }

        return Map.of(
                "disponibleEn", "sin stock",
                "stock", 0
        );
    }
    
    public List<Stock> findByVehiculoIdAndConcesionariaId(Long vehiculoId, Long concesionariaId){
        return repository.findByVehiculoIdAndConcesionariaId(vehiculoId, concesionariaId);
        
    }
    
    public List<Stock> findByVehiculoIdAndConcesionariaIdIsNull(Long vehiculoId){
        return repository.findByVehiculoIdAndConcesionariaIdIsNull(vehiculoId);

    }

    public StockDTO save(StockDTO dto) {
        Stock entidad = mapper.map(dto, Stock.class);
        entidad.setVehiculo(vehiculoRepository.findById(dto.getVehiculoId())
                .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado")));
        return mapearDTO(repository.save(entidad));
    }

    public void descontarStock(Long vehiculoId, Integer cantidad) {
        Optional<Stock> stockOpt = repository.findByVehiculoId(vehiculoId);

        if (stockOpt.isEmpty()) throw new RuntimeException("No hay stock para este vehículo");

        Stock stock = stockOpt.get();
        if (stock.getCantidad() < cantidad) {
            throw new RuntimeException("Stock insuficiente");
        }

        stock.setCantidad(stock.getCantidad() - cantidad);
        repository.save(stock);
    }

    private StockDTO mapearDTO(Stock s) {
        StockDTO dto = mapper.map(s, StockDTO.class);
        dto.setVehiculoId(s.getVehiculo().getId());
        return dto;
    }
}
