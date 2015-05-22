package com.proyecto.gmwork.proyectoandroid.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Date;

/**
 * Created by mateo on 20/05/15.
 */
@DatabaseTable(tableName = "CategoriaLog")
public class CategoriaLog {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private String operacion;
    @DatabaseField
    private String fecha;
    @DatabaseField
    private long idCategoria;

    public CategoriaLog() {
    }

    public CategoriaLog(String operacion, String fecha, long idCategoria) {
        this.operacion = operacion;
        this.fecha = fecha;
        this.idCategoria = idCategoria;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public long getidCategoria() {
        return idCategoria;
    }

    public void setidCategoria(long idCategoria) {
        this.idCategoria = idCategoria;
    }
}
