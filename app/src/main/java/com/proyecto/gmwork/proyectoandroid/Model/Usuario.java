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
 *
 *
 * Clase de los usuarios hace el mapeo de la base de de datos segun ormlite
 */

@DatabaseTable(tableName = "usuario")
public class Usuario implements Serializable {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private String nif;
    @DatabaseField
    private String nombre;
    @DatabaseField
    private String apellidos;
    @DatabaseField
    private String calle;
    @DatabaseField
    private String poblacion;
    @DatabaseField
    private boolean administrador;
    @DatabaseField
    private String username;
    @DatabaseField
    private String password;
    @DatabaseField
    private boolean baja;
    @ForeignCollectionField
    private ForeignCollection<Cliente> clientes;

    public Usuario() {
    }

    public Usuario(String nif, String nombre, String apellidos, String calle, String poblacion, boolean administrador, String username, String password) {
        this.nif = nif;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.calle = calle;
        this.poblacion = poblacion;
        this.administrador = administrador;
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public boolean isBaja() {
        return baja;
    }

    public void setBaja(boolean baja) {
        this.baja = baja;
    }

    public ForeignCollection<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ForeignCollection<Cliente> clientes) {
        this.clientes = clientes;
    }

    public void addClientes(Cliente cli) {
        cli.setUsu(this);
        this.clientes.add(cli);

    }

    @Override
    public String toString() {
        return "Usuario[" +
                "id=" + id +
                ", nif='" + nif + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", calle='" + calle + '\'' +
                ", poblacion='" + poblacion + '\'' +
                ", administrador=" + administrador +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", clientes=" + clientes +
                ']';
    }
}
