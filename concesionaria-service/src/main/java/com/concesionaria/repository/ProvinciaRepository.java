package com.concesionaria.repository;

import com.concesionaria.model.Provincia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {

    @Query("SELECT p FROM Provincia p WHERE " +
       "(:paisId IS NULL OR p.pais.id = :paisId) " +
       "AND (:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))")
    List<Provincia> buscarPorFiltros(@Param("paisId") Long paisId, @Param("nombre") String nombre);

}