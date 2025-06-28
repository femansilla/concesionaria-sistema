package com.vehiculos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vehiculos.model.TipoVehiculo;

public interface TipoVehiculoRepository extends JpaRepository<TipoVehiculo, Long>, JpaSpecificationExecutor<TipoVehiculo> {
    @Query("SELECT t FROM TipoVehiculo t " +
           "WHERE (:descripcion IS NULL OR LOWER(t.descripcion) LIKE LOWER(CONCAT('%', :descripcion, '%'))) " +
           "AND (:aniosGarantia IS NULL OR t.garantiaAnios = :aniosGarantia) " +
           "AND (:kilometrosGarantia IS NULL OR t.garantiaKilometros = :kilometrosGarantia)")
    List<TipoVehiculo> buscarPorFiltros(
        @Param("descripcion") String descripcion,
        @Param("aniosGarantia") Integer aniosGarantia,
        @Param("kilometrosGarantia") Integer kilometrosGarantia
    );

}