package com.Evaluador.springboot_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Empleado")
@Inheritance(strategy = InheritanceType.JOINED)
public class Empleado extends Persona{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario unUsuario;

    private String cargo;

    public Empleado() {
    }

    public Empleado(Usuario unUsuario, String cargo) {
        this.unUsuario = unUsuario;
        this.cargo = cargo;
    }

    public Empleado(String nonbres, String direccion, String telefono, Usuario unUsuario, String cargo) {
        super(nonbres, direccion, telefono);
        this.unUsuario = unUsuario;
        this.cargo = cargo;
    }

    public Usuario getUnUsuario() {
        return unUsuario;
    }

    public void setUnUsuario(Usuario unUsuario) {
        this.unUsuario = unUsuario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
