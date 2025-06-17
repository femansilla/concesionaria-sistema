package com.vehiculos.repository;

import com.vehiculos.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findByConcesionariaId(Long concesionariaId);

    Optional<Stock> findByVehiculoId(Long vehiculoId);

    List<Stock> findByVehiculoIdAndConcesionariaId(Long vehiculoId, Long concesionariaId);

    List<Stock> findByVehiculoIdAndConcesionariaIdIsNull(Long vehiculoId);
}