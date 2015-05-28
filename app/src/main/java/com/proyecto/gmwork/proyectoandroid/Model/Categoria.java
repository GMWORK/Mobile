package com.proyecto.gmwork.proyectoandroid.Model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 05/05/2015.
 */
@DatabaseTable(tableName = "categoria")
public class Categoria  implements Serializable {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private String nombre;
    @DatabaseField
    private double descuento;
    @DatabaseField
    private boolean baja;

    @ForeignCollectionField
    private ForeignCollection<Producto> productos;

    public Categoria() {
    }

    public Categoria(String nombre, double descuento) {
        this.nombre = nombre;
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

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public ForeignCollection<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ForeignCollection<Producto> productos) {
        this.productos = productos;
    }

    public void addProducto(Producto pro) throws SQLException {
        pro.setCategoria(this);
        this.productos.add(pro);


    }

    public boolean isBaja() {
        return baja;
    }

    public void setBaja(boolean baja) {
        this.baja = baja;
    }

    @Override
    public String toString() {
        return "Categoria[" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descuento=" + descuento +
                ", productos=" + productos +
                ']';
    }
}
