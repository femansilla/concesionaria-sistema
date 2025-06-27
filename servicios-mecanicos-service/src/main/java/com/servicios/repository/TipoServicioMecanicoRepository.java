package com.servicios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.servicios.model.TipoServicioMecanico;

public interface TipoServicioMecanicoRepository extends JpaRepository<TipoServicioMecanico, Long>, JpaSpecificationExecutor<TipoServicioMecanico> {

    @Query("SELECT t FROM TipoServicioMecanico t WHERE " +
       "(:descripcion IS NULL OR LOWER(t.descripcion) LIKE LOWER(CONCAT('%', :descripcion, '%')))")
    List<TipoServicioMecanico> buscarPorDescripcion(@Param("descripcion") String descripcion);

}