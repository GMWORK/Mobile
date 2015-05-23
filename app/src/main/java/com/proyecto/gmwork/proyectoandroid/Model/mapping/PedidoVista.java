package com.proyecto.gmwork.proyectoandroid.Model.mapping;

import org.joda.time.DateTime;

import java.sql.Date;

/**
 * Created by Mateo on 22/05/2015.
 */
public class PedidoVista {
    private int id;
    private String fechaEntrega;
    private String estado;
    private int clienteid;
    private double total;
    private String Op;
    private DateTime fecha;
    private int idPedido;

    public PedidoVista() {
    }

    public PedidoVista(String fechaEntrega, String estado, int clienteid, double total, String op, DateTime fecha, int idPedido) {
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
        this.clienteid = clienteid;
        this.total = total;
        Op = op;
        this.fecha = fecha;
        this.idPedido = idPedido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public void setFecha(DateTime fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getClienteid() {
        return clienteid;
    }

    public void setClienteid(int clienteid) {
        this.clienteid = clienteid;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getOp() {
        return Op;
    }

    public void setOp(String op) {
        Op = op;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public DateTime getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "PedidoVista[" +
                "id=" + id +
                ", fechaEntrega='" + fechaEntrega + '\'' +
                ", estado='" + estado + '\'' +
                ", clienteid=" + clienteid +
                ", total=" + total +
                ", Op='" + Op + '\'' +
                ", fecha=" + fecha +
                ", idPedido=" + idPedido +
                ']';
    }
}
