package com.Evaluador.springboot_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Prenda")
@Inheritance(strategy = InheritanceType.JOINED)
public class Prenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_prenda;

    @Column(name = "refer", nullable = false, unique = true, length = 250)
    private String refer;

    @Column(name = "color", nullable = false, length = 250)
    private String color;

    @Column(name = "marca", nullable = false, length = 250)
    private String marca;

    public Prenda() {
    }

    public Prenda(String refer, String color, String marca) {
        this.refer = refer;
        this.color = color;
        this.marca = marca;
    }

    public Long getId_prenda() {
        return id_prenda;
    }

    public void setId_prenda(Long id_prenda) {
        this.id_prenda = id_prenda;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
