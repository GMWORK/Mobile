package com.proyecto.gmwork.proyectoandroid.Model;

import com.j256.ormlite.field.DatabaseField;

import java.sql.Date;

/**
 * Created by mateo on 08/05/15.
 */
public class PedidoLog {

    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private String operacion;
    @DatabaseField
    private Date fecha;
    @DatabaseField
    private long idPedido;

    public PedidoLog(String operacion, Date fecha, long idPedido) {
        this.operacion = operacion;
        this.fecha = fecha;
        this.idPedido = idPedido;
    }

    public PedidoLog() {
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
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
