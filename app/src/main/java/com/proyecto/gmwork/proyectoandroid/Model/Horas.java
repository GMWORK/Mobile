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
}
