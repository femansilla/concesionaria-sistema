package com.vehiculos.repository;

import com.vehiculos.model.Vehiculo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long>, JpaSpecificationExecutor<Vehiculo> {
    @Query("SELECT v FROM Vehiculo v " +
        "WHERE (:marca IS NULL OR v.marca = :marca) " +
        "AND (:modelo IS NULL OR v.modelo = :modelo) " +
        "AND (:tipoId IS NULL OR v.tipoVehiculo.id = :tipoId) " +
        "AND (:precioMin IS NULL OR v.precioUnidad >= :precioMin) " +
        "AND (:precioMax IS NULL OR v.precioUnidad <= :precioMax)")
    List<Vehiculo> buscarPorFiltros(
        @Param("marca") String marca,
        @Param("modelo") String modelo,
        @Param("tipoId") Long tipoId,
        @Param("precioMin") BigDecimal precioMin,
        @Param("precioMax") BigDecimal precioMax
    );

}
