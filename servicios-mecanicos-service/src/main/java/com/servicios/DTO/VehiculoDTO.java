package com.servicios.DTO;

import java.math.BigDecimal;

public class VehiculoDTO {
    private Long id;
    private String marca;
    private String modelo;
    private TipoVehiculoDTO tipoVehiculo;
    private Integer anio;
    private BigDecimal precioUnidad;

    public VehiculoDTO() {}
    
    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public BigDecimal getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(BigDecimal precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public TipoVehiculoDTO getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculoDTO tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }
}

