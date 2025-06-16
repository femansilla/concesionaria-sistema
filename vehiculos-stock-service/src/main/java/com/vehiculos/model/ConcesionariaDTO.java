package com.vehiculos.model;

import java.time.LocalDate;

public class ConcesionariaDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private LocalDate fechaApertura;
    private PaisDTO pais;
    private LocalidadDTO localidad;

    // getters y setters
    public LocalidadDTO getLocalidad() {
        return this.localidad;
    }

}