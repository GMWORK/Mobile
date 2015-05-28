package com.proyecto.gmwork.proyectoandroid.Model.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.Model.Horas;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by mateo on 20/05/15.
 */
public class HoraDAO {
    private Dao<Horas, Long> daoHora;
    private OpenLiteHelper clidao;
    private Context con;

    public HoraDAO(Context con) {

        try {
            clidao = new OpenLiteHelper(con);
            this.daoHora = clidao.getDAOHoras();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.con = con;
    }

    public void addPedido(Horas cat) {
        try {
            daoHora.createOrUpdate(cat);
        } catch (SQLException ex) {
            Log.i("errorSQL", ex.getMessage());
        }
    }

    public List<Horas> getPedidos() {
        List<Horas> todos = null;
        try {
            todos = daoHora.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    public Horas getUltimaBajada() {

        Horas client = null;
        try {
            client = daoHora.queryForEq("id",1).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }
    public void guardarHoraBajada(Horas hora) {
        Horas uBa = getUltimaBajada();
        try {
            uBa.setFecha(hora.getFecha());
            daoHora.update(uBa);

        } catch (SQLException ex) {
            Logger.getLogger(HoraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardarHoraSubidacd(Horas hora) {
        Horas uSu = getUltimaSubida();
        try {
            uSu.setFecha(hora.getFecha());
            daoHora.update(uSu);

        } catch (SQLException ex) {
            Logger.getLogger(HoraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Horas getUltimaSubida () {
        Horas hora = null;
        try{
        hora =daoHora.queryForEq("id",2).get(0);
        if(hora !=null){
            return  hora;
        }else{
            hora.setFecha("1999-07-15T00:00:00+02:00");
            hora.setId(2);
            return hora;

        }}catch(IndexOutOfBoundsException ex){
            hora = new Horas();
            hora.setFecha("1999-07-15T00:00:00+02:00");
            hora.setId(2);
            return hora;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }



    }
    public boolean removeHora(int id) {
        try {
            daoHora.delete(daoHora.queryForEq("id", id));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean EditarPedido(Horas cat) {
        try {
            daoHora.update(cat);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
