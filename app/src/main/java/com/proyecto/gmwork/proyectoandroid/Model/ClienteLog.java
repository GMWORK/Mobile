package com.proyecto.gmwork.proyectoandroid.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Date;

/**
 * Created by mateo on 11/05/15.
 */
@DatabaseTable(tableName = "ClienteLog")
public class ClienteLog {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private String Op;
    @DatabaseField
    private String fecha;
    @DatabaseField
    private long idCliente;

    public ClienteLog() {
    }

    public ClienteLog(String Op, String fecha, long idCliente) {
        this.Op = Op;
        this.fecha = fecha;
        this.idCliente = idCliente;
    }

    public String getOperacion() {
        return Op;
    }

    public void setOperacion(String operacion) {
        this.Op = operacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }
}
