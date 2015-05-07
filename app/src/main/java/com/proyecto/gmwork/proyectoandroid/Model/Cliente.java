package com.proyecto.gmwork.proyectoandroid.Model;

import android.support.annotation.NonNull;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mateo on 30/04/15.
 */
@DatabaseTable(tableName = "CLIENTE")
public class Cliente implements Serializable {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private String nif;
    @DatabaseField
    private String nombre;
    @DatabaseField
    private String apellidos;
    @DatabaseField
    private double longitud;
    @DatabaseField
    private double latitud;
    @DatabaseField
    private String calle;
    @DatabaseField
    private String poblacion;
    @DatabaseField
    private Date proximaVisita;
    @ForeignCollectionField
    private ForeignCollection<Pedido> pedido ;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Usuario usu;

    public Cliente() {
    }
    
    public Cliente(String nif, String nombre, String apellidos, double longitud, double latitud, String calle, String poblacion, Date proximaVisita) {
        this.nif = nif;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.longitud = longitud;
        this.latitud = latitud;
        this.calle = calle;
        this.poblacion = poblacion;
        this.proximaVisita = proximaVisita;

    }
    
    public long getId() {
        return id;
    }
    
    private void setId(long id) {
        this.id = id;
    }
    
    public String getNif() {
        return nif;
    }
    
    public void setNif(String nif) {
        this.nif = nif;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellidos() {
        return apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public double getLongitud() {
        return longitud;
    }
    
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    
    public double getLatitud() {
        return latitud;
    }
    
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }
    
    public String getCalle() {
        return calle;
    }
    
    public void setCalle(String calle) {
        this.calle = calle;
    }
    
    public String getPoblacion() {
        return poblacion;
    }
    
    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }
    
    public Date getProximaVisita() {
        return proximaVisita;
    }
    
    public void setProximaVisita(Date proximaVisita) {
        this.proximaVisita = proximaVisita;
    }


    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

    public ForeignCollection getPedido() {
        return pedido;
    }

    public void setPedido(ForeignCollection pedido) {
        this.pedido = pedido;
    }

    public void addPedido(Pedido ped) {
        this.pedido.add(ped);
        ped.setCliente(this);
    }
}
