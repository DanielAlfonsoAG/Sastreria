package com.Evaluador.springboot_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Disfraz")
@Inheritance(strategy = InheritanceType.JOINED)
public class Disfraz extends Prenda{

    private String nombre;

    public Disfraz() {
    }

    public Disfraz(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
