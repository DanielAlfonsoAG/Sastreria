package com.Evaluador.springboot_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Cliente")
@Inheritance(strategy = InheritanceType.JOINED)
public class Cliente extends Persona{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario unUsuario;

    public Cliente() {
    }

    public Cliente(String carga, Usuario unUsuario) {
        this.unUsuario = unUsuario;
    }

    public Cliente(String nonbres, String direccion, String telefono, Usuario unUsuario) {
        super(nonbres, direccion, telefono);
        this.unUsuario = unUsuario;
    }

    public Usuario getUnUsuario() {
        return unUsuario;
    }

    public void setUnUsuario(Usuario unUsuario) {
        this.unUsuario = unUsuario;
    }
}
