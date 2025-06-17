package com.ventas.model;

import java.time.LocalDateTime;

public class ServicioMecanicoDTO {
    private Long id;
    private TipoServicioMecanicoDTO servicio;
    private Integer kilometros;
    private Boolean enGarantia;
    private LocalDateTime fechaCarga;

    public ServicioMecanicoDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoServicioMecanicoDTO getServicio() {
        return servicio;
    }

    public void setServicio(TipoServicioMecanicoDTO servicio) {
        this.servicio = servicio;
    }

    public Integer getKilometros() {
        return kilometros;
    }

    public void setKilometros(Integer kilometros) {
        this.kilometros = kilometros;
    }

    public Boolean getEnGarantia() {
        return enGarantia;
    }

    public void setEnGarantia(Boolean enGarantia) {
        this.enGarantia = enGarantia;
    }

    public LocalDateTime getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(LocalDateTime fechaCarga) {
        this.fechaCarga = fechaCarga;
    }
}
