package com.vehiculos.repository;

import com.vehiculos.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findByConcesionariaId(Long concesionariaId);
}