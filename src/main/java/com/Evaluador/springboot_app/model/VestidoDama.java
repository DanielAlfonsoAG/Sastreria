package com.Evaluador.springboot_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "VestidoDama")
@Inheritance(strategy = InheritanceType.JOINED)
public class VestidoDama extends Prenda{

    private String pedreria;
    private String altura;
    private String cantPiezas;

    public VestidoDama() {
    }

    public VestidoDama(String pedreria, String altura, String cantPiezas) {
        this.pedreria = pedreria;
        this.altura = altura;
        this.cantPiezas = cantPiezas;
    }

    public String getPedreria() {
        return pedreria;
    }

    public void setPedreria(String pedreria) {
        this.pedreria = pedreria;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getCantPiezas() {
        return cantPiezas;
    }

    public void setCantPiezas(String cantPiezas) {
        this.cantPiezas = cantPiezas;
    }

}
