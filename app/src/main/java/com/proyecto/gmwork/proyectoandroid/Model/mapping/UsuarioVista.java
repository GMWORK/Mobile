package com.proyecto.gmwork.proyectoandroid.Model.mapping;

import org.joda.time.DateTime;

/**
 * Created by Mateo on 22/05/2015.
 */
public class UsuarioVista {
    private long id;
    private String nif;
    private String nombre;
    private String apellidos;
    private String calle;
    private String poblacion;
    private boolean administrador;
    private String username;
    private String password;
    private DateTime fecha;
    private String Op;

    public UsuarioVista() {
    }

    public UsuarioVista(String nif, String nombre, String apellidos, String calle, String poblacion, boolean administrador, String username, String password, DateTime fecha, String op) {
        this.nif = nif;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.calle = calle;
        this.poblacion = poblacion;
        this.administrador = administrador;
        this.username = username;
        this.password = password;
        this.fecha = fecha;
        Op = op;
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

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
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

    public DateTime getFecha() {
        return fecha;
    }

    public void setFecha(DateTime fecha) {
        this.fecha = fecha;
    }

    public String getOp() {
        return Op;
    }

    public void setOp(String op) {
        Op = op;
    }

    @Override
    public String toString() {
        return "UsuarioVista[" +
                "id=" + id +
                ", nif='" + nif + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", calle='" + calle + '\'' +
                ", poblacion='" + poblacion + '\'' +
                ", administrador=" + administrador +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fecha=" + fecha +
                ", Op='" + Op + '\'' +
                ']';
    }
}
