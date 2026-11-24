package com.Evaluador.springboot_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TrajeCaballero")
@Inheritance(strategy = InheritanceType.JOINED)
public class TrajeCaballero extends Prenda{

    private String tipo;
    private String aderezo;

    public TrajeCaballero() {
    }

    public TrajeCaballero(String tipo, String aderezo) {
        this.tipo = tipo;
        this.aderezo = aderezo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAderezo() {
        return aderezo;
    }

    public void setAderezo(String aderezo) {
        this.aderezo = aderezo;
    }
}
