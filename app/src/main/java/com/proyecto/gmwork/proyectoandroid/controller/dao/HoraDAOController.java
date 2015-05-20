package com.proyecto.gmwork.proyectoandroid.controller.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.Model.Horas;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mateo on 20/05/15.
 */
public class HoraDAOController {
    private Dao<Horas, Long> daoHora;
    private OpenLiteHelper clidao;
    private Context con;

    public HoraDAOController(Context con) throws SQLException {
        clidao = new OpenLiteHelper(con);
        this.daoHora = clidao.getDAOHoras();
        this.con = con;
    }

    public void addPedido(Horas cat) {
        try {
            daoHora.createOrUpdate(cat);
        } catch (SQLException ex) {
            Log.i("errorSQL", ex.getMessage());
        }
    }

    public List<Horas> getPedidos() throws SQLException {
        List<Horas> todos = daoHora.queryForAll();
        return todos;
    }

    public Horas getUltimaBajada() throws SQLException {

        Horas client = daoHora.queryForId((long) 1);
        return client;
    }
    public Horas getUltimaSubida () throws SQLException {
        return  daoHora.queryForId((long) 2);
    }
    public void removeHora(int id) throws SQLException {
        daoHora.delete(daoHora.queryForEq("id", id));
    }

    public void EditarPedido(Horas cat) throws SQLException {
        daoHora.update(cat);
    }
}
