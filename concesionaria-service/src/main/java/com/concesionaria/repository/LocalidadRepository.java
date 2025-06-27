package com.concesionaria.repository;

import com.concesionaria.model.Localidad;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocalidadRepository extends JpaRepository<Localidad, Long> {

    @Query("SELECT l FROM Localidad l WHERE " +
       "(:provinciaId IS NULL OR l.provincia.id = :provinciaId) " +
       "AND (:nombre IS NULL OR LOWER(l.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))")
    List<Localidad> buscarPorFiltros(@Param("provinciaId") Long provinciaId,
                                 @Param("nombre") String nombre);

}