package com.servicios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.servicios.model.TipoServicioMecanico;

public interface TipoServicioMecanicoRepository extends JpaRepository<TipoServicioMecanico, Long>, JpaSpecificationExecutor<TipoServicioMecanico> {}