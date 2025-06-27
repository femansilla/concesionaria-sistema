package com.concesionaria.repository;

import com.concesionaria.model.Concesionaria;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConcesionariaRepository extends JpaRepository<Concesionaria, Long> {

    @Query("SELECT c FROM Concesionaria c " +
       "WHERE (:paisId IS NULL OR c.pais.id = :paisId) " +
       "AND (:provinciaId IS NULL OR c.provincia.id = :provinciaId) " +
       "AND (:localidadId IS NULL OR c.localidad.id = :localidadId) " +
       "AND (:nombre IS NULL OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))")
    List<Concesionaria> buscarPorFiltros(
            @Param("paisId") Long paisId,
            @Param("provinciaId") Long provinciaId,
            @Param("localidadId") Long localidadId,
            @Param("nombre") String nombre);
}