package com.vehiculos.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vehiculos.model.Stock;
import com.vehiculos.repository.StockRepository;

@Service
public class StockService {

    private final StockRepository repository;

    public StockService(StockRepository repository) {
        this.repository = repository;
    }

    public List<Stock> findAll() {
        return repository.findAll();
    }

    public Optional<Stock> findById(Long id) {
        return repository.findById(id);
    }

    public List<Stock> findByConcesionariaId(Long concesionariaId){
        return repository.findByConcesionariaId(concesionariaId);
    }
    
    public Optional<Stock> findByVehiculoId(Long vehiculoId){
        return repository.findByVehiculoId(vehiculoId);
    }
    
    public List<Stock> findByVehiculoIdAndConcesionariaId(Long vehiculoId, Long concesionariaId){
        return repository.findByVehiculoIdAndConcesionariaId(vehiculoId, concesionariaId);
        
    }
    
    public List<Stock> findByVehiculoIdAndConcesionariaIdIsNull(Long vehiculoId){
        return repository.findByVehiculoIdAndConcesionariaIdIsNull(vehiculoId);

    }

    public Stock save(Stock stock) {
        return repository.save(stock);
    }

    public boolean descontarStock(Long vehiculoId, Integer cantidad) {
        Optional<Stock> stockOpt = repository.findByVehiculoId(vehiculoId);

        if (stockOpt.isEmpty()) return false;

        Stock stock = stockOpt.get();

        if (stock.getCantidad() < cantidad) {
            return false;
        }

        stock.setCantidad(stock.getCantidad() - cantidad);
        repository.save(stock);

        return true;
    }
}
