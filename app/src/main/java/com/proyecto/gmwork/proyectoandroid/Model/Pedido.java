package com.proyecto.gmwork.proyectoandroid.Model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.proyecto.gmwork.proyectoandroid.Model.PedidoProducto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mateo on 30/04/15.
 */
@DatabaseTable(tableName = "pedido")
public class Pedido  implements Serializable {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private String fechaEntrega;
    @DatabaseField
    private String estado;
    @DatabaseField
    private double total;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Cliente cliente;
    private boolean baja;
    @ForeignCollectionField
    private ForeignCollection<PedidoProducto> liniaProducto;
    
    public Pedido() {
    }
    
    public Pedido(String fechaEntrega, String estado) {
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
    }

    public Pedido(String fecha, String estado, double total) {
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
        this.total = total;
    }

    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Cliente getCliente() {
        return cliente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ForeignCollection<PedidoProducto> getLiniaProducto() {
        return liniaProducto;
    }

    public void setLiniaProducto(ForeignCollection<PedidoProducto> liniaProducto) {
        this.liniaProducto = liniaProducto;
    }

    public void addLiniaProducto(PedidoProducto pPro) {

        this.liniaProducto.add(pPro);
        pPro.setPedido(this);

        
    }

    public boolean isBaja() {
        return baja;
    }

    public void setBaja(boolean baja) {
        this.baja = baja;
    }

    @Override
    public String toString() {
        return "Pedido[" +
                "id=" + id +
                ", fecha='" + fechaEntrega + '\'' +
                ", estado='" + estado + '\'' +
                ", total=" + total +
                ", cliente=" + cliente +
                ", liniaProducto=" + liniaProducto +
                ']';
    }
}
