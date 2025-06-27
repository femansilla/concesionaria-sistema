package com.concesionaria.repository;

import com.concesionaria.model.Pais;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaisRepository extends JpaRepository<Pais, Long> {
    List<Pais> findByNombreContainingIgnoreCase(String nombre);
}