package com.proyecto.gmwork.proyectoandroid.Model.mapping;

import org.joda.time.DateTime;

/**
 * Created by Mateo on 22/05/2015.
 */
public class PedidoProductoVista {
    private int productoid;
    private int pedidoid;
    private String cantidad;
    private String Op;
    private DateTime fecha;

    public PedidoProductoVista() {
    }

    public PedidoProductoVista(int productoid, int pedidoid, String cantidad, String op, DateTime fecha) {
        this.productoid = productoid;
        this.pedidoid = pedidoid;
        this.cantidad = cantidad;
        Op = op;
        this.fecha = fecha;
    }

    public int getProductoid() {
        return productoid;
    }

    public void setProductoid(int productoid) {
        this.productoid = productoid;
    }

    public int getPedidoid() {
        return pedidoid;
    }

    public void setPedidoid(int pedidoid) {
        this.pedidoid = pedidoid;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
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
        return "PedidoProductoVista[" +
                "productoid=" + productoid +
                ", pedidoid=" + pedidoid +
                ", cantidad='" + cantidad + '\'' +
                ", Op='" + Op + '\'' +
                ", fecha=" + fecha +
                ']';
    }
}
