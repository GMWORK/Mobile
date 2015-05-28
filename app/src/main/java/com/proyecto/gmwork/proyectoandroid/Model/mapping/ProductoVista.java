package com.proyecto.gmwork.proyectoandroid.Model.mapping;

import org.joda.time.DateTime;

import java.util.Arrays;

/**
 * Created by Mateo on 22/05/2015.
 */
public class ProductoVista {
    private long id;
    private String nombre;
    private double precio;
    private String img;
    private boolean inhabilitats;
    private double descuento;
    private int categoriaid;
    private String Op;
    private DateTime fecha;

    public ProductoVista() {
    }

    public ProductoVista(String nombre, double precio,String img, boolean inhabilitats, double descuento, int categoriaid, String op, DateTime fecha) {
        this.nombre = nombre;
        this.precio = precio;
        this.img = img;
        this.inhabilitats = inhabilitats;
        this.descuento = descuento;
        this.categoriaid = categoriaid;
        Op = op;
        this.fecha = fecha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isInhabilitats() {
        return inhabilitats;
    }

    public void setInhabilitats(boolean inhabilitats) {
        this.inhabilitats = inhabilitats;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public int getCategoriaid() {
        return categoriaid;
    }

    public void setCategoriaid(int categoriaid) {
        this.categoriaid = categoriaid;
    }

    public String getOp() {
        return Op;
    }

    public void setOp(String op) {
        Op = op;
    }

    public DateTime getFecha() {
        return fecha;
    }

    public void setFecha(DateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "ProductoVista{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", img='" + img + '\'' +
                ", inhabilitats=" + inhabilitats +
                ", descuento=" + descuento +
                ", categoriaid=" + categoriaid +
                ", Op='" + Op + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}

