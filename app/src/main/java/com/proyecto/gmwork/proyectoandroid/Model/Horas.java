package com.proyecto.gmwork.proyectoandroid.Model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by mateo on 20/05/15.
 */
public class Horas {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private String fecha;

    public Horas() {
    }

    public Horas(String fecha) {
        this.fecha = fecha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
