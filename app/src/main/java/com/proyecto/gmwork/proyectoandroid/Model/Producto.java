package com.proyecto.gmwork.proyectoandroid.Model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 05/05/2015.
 */

@DatabaseTable(tableName = "PRODUCTO")
public class Producto implements Serializable{
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private String nombre;
    @DatabaseField
    private double precio;
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] img;
    @DatabaseField
    private boolean inhabilitats;
    @DatabaseField
    private double descuento;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Categoria categoria;
    @ForeignCollectionField
    private ForeignCollection<PedidoProducto> liniaPedidos;

    public Producto() {
    }

    public Producto(String nombre, double precio, byte[] img, boolean inhabilitats, double descuento) {
        this.nombre = nombre;
        this.precio = precio;
        this.img = img;
        this.inhabilitats = inhabilitats;
        this.descuento = descuento;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public boolean isInhabilitats() {
        return inhabilitats;
    }

    public void setInhabilitats(boolean inhabilitats) {
        this.inhabilitats = inhabilitats;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public ForeignCollection<PedidoProducto> getLiniaPedidos() {
        return liniaPedidos;
    }

    public void setLiniaPedidos(ForeignCollection<PedidoProducto> liniaPedidos) {
        this.liniaPedidos = liniaPedidos;
    }

    public void addLiniaPedido(PedidoProducto liPro) {
        this.liniaPedidos.add(liPro);
        liPro.setProducto(this);
    }
}
