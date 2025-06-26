package com.clientes.repository;

import com.clientes.model.Cliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClienteRepository extends JpaRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente> {


    @Query("SELECT c FROM Cliente c WHERE " +
       "(:nombre IS NULL OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
       "(:apellido IS NULL OR LOWER(c.apellido) LIKE LOWER(CONCAT('%', :apellido, '%'))) AND " +
       "(:dni IS NULL OR c.dni = :dni)")
    List<Cliente> buscarPorFiltros(@Param("nombre") String nombre,
                                @Param("apellido") String apellido,
                                @Param("dni") String dni);


}