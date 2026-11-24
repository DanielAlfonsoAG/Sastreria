package com.Evaluador.springboot_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Prendasvendidas")
public class Prendasvendidas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_prendavendida;

    @Column(name = "precio", nullable = false, length = 250)
    private double precio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prenda")
    private Prenda prenda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "creado_por", nullable = false)
    private Usuario creadoPor;

    public Prendasvendidas() {
    }

    public Prendasvendidas(Long id_prendavendida, double precio, Prenda prenda, Cliente cliente, Usuario creadoPor) {
        this.id_prendavendida = id_prendavendida;
        this.precio = precio;
        this.prenda = prenda;
        this.cliente = cliente;
        this.creadoPor = creadoPor;
    }

    public Long getId_prendavendida() {
        return id_prendavendida;
    }

    public void setId_prendavendida(Long id_prendavendida) {
        this.id_prendavendida = id_prendavendida;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Prenda getPrenda() {
        return prenda;
    }

    public void setPrenda(Prenda prenda) {
        this.prenda = prenda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }
}
