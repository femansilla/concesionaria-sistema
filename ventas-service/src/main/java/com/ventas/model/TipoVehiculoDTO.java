package com.ventas.model;

public class TipoVehiculoDTO {
    private Long id;
    private String descripcion;
    private Integer garantiaAnios;
    private Integer garantiaKilometros;
    private Double adicionalServicio;

    public TipoVehiculoDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getGarantiaAnios() {
        return garantiaAnios;
    }

    public void setGarantiaAnios(Integer garantiaAnios) {
        this.garantiaAnios = garantiaAnios;
    }

    public Integer getGarantiaKilometros() {
        return garantiaKilometros;
    }

    public void setGarantiaKilometros(Integer garantiaKilometros) {
        this.garantiaKilometros = garantiaKilometros;
    }

    public Double getAdicionalServicio() {
        return adicionalServicio;
    }
}