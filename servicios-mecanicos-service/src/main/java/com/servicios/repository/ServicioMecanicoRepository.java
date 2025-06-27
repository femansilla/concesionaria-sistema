package com.servicios.repository;

import com.servicios.model.ServicioMecanico;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServicioMecanicoRepository extends JpaRepository<ServicioMecanico, Long> {

    @Query("SELECT s FROM ServicioMecanico s WHERE " +
       "(:clienteId IS NULL OR s.clienteId = :clienteId) AND " +
       "(:vehiculoId IS NULL OR s.vehiculoId = :vehiculoId) AND " +
       "(:enGarantia IS NULL OR s.enGarantia = :enGarantia) AND " +
       "(:tipoServicioId IS NULL OR s.servicio.id = :tipoServicioId)")
    List<ServicioMecanico> buscarConFiltros(@Param("clienteId") Long clienteId,
                                      @Param("vehiculoId") Long vehiculoId,
                                      @Param("enGarantia") Boolean enGarantia,
                                      @Param("tipoServicioId") Long tipoServicioId);

    
    List<ServicioMecanico> findByVehiculoId(Long vehiculoId);
}