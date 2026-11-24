package com.Evaluador.springboot_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Persona")
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_persona;

    @Column(name = "nombres", nullable = false, unique = true, length = 250)
    private String nombres;

    @Column(name = "direccion", nullable = false, length = 250)
    private String direccion;

    @Column(name = "telefono", nullable = false, length = 250)
    private String telefono;

    public Persona() {
    }

    public Persona(String nombres, String direccion, String telefono) {
        this.nombres = nombres;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public Long getId_persona() {
        return id_persona;
    }

    public void setId_persona(Long id_persona) {
        this.id_persona = id_persona;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nonbres) {
        this.nombres = nonbres;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
