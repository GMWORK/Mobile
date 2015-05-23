package com.proyecto.gmwork.proyectoandroid.Model.mapping;

import org.joda.time.DateTime;

import java.sql.Date;
import java.util.Arrays;

/**
 * Created by Mateo on 22/05/2015.
 */
public class ClienteVista {
    private int id;
    private String nif;
    private String nombre;
    private String apellidos;
    private double longitud;
    private double latitud;
    private String calle;
    private String poblacion;
    private int usuarioid;
    private String proximaVisita;
    private byte[] img;
    private DateTime fecha;
    private String Op;

    public ClienteVista() {
    }

    public ClienteVista(String nif, String nombre, String apellidos, double longitud, double latitud, String calle, String poblacion, int usuarioid, String proximaVisita, byte[] img, DateTime fecha, String op) {
        this.nif = nif;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.longitud = longitud;
        this.latitud = latitud;
        this.calle = calle;
        this.poblacion = poblacion;
        this.usuarioid = usuarioid;
        this.proximaVisita = proximaVisita;
        this.img = img;
        this.fecha = fecha;
        Op = op;
    }

    public DateTime getFecha() {
        return fecha;
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

    public int getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(int usuarioid) {
        this.usuarioid = usuarioid;
    }

    public String getProximaVisita() {
        return proximaVisita;
    }

    public void setProximaVisita(String proximaVisita) {
        this.proximaVisita = proximaVisita;
    }

    public void setFecha(DateTime fecha) {
        this.fecha = fecha;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getOp() {
        return Op;
    }

    public void setOp(String op) {
        Op = op;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ClienteVista[" +
                "id=" + id +
                ", nif='" + nif + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", longitud=" + longitud +
                ", latitud=" + latitud +
                ", calle='" + calle + '\'' +
                ", poblacion='" + poblacion + '\'' +
                ", usuarioid=" + usuarioid +
                ", proximaVisita='" + proximaVisita + '\'' +
                ", img=" + Arrays.toString(img) +
                ", fecha=" + fecha +
                ", Op='" + Op + '\'' +
                ']';
    }
}
