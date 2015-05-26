package com.proyecto.gmwork.proyectoandroid.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Date;

/**
 * Created by mateo on 08/05/15.
 */
@DatabaseTable(tableName = "PedidoLog")
public class PedidoLog {

    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private String Op;
    @DatabaseField
    private String fecha;
    @DatabaseField
    private long idPedido;

    public PedidoLog(String Op, String fecha, long idPedido) {
        this.Op = Op;
        this.fecha = fecha;
        this.idPedido = idPedido;
    }

    public PedidoLog() {
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

    public long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }
}
