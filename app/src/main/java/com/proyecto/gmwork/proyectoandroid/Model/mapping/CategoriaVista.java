package com.proyecto.gmwork.proyectoandroid.Model.mapping;

import org.joda.time.DateTime;

import java.sql.Date;

/**
 * Created by Mateo on 22/05/2015.
 */
public class CategoriaVista {
    private long id;
    private String nombre;
    private double descuento;
    private String Op;
    private DateTime fecha;

    public CategoriaVista() {
    }

    public CategoriaVista(String nombre, double descuento, String op, DateTime fecha) {
        this.nombre = nombre;
        this.descuento = descuento;
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

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
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
}
