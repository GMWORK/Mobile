package com.proyecto.gmwork.proyectoandroid.controller.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.Model.PedidoLog;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mateo on 30/04/15.
 */
public class PedidoDAOController {
    private Dao<Pedido, Long> daoPe;
    private Dao<PedidoLog, Long> daoPelog;
    private OpenLiteHelper clidao;
    private Context con;

    public PedidoDAOController(Context con) throws SQLException {
        clidao = new OpenLiteHelper(con);
        this.daoPe = clidao.getDAOPedido();
        daoPelog = clidao.getDAOPedidoLog();
        this.con = con;
    }

    public void addPedido(Pedido cat) {
        try {
            daoPe.create(cat);
        } catch (SQLException ex) {
            Log.i("errorSQL", ex.getMessage());
        }
    }

    public List<Pedido> getPedidos() throws SQLException {
        List<Pedido> todos = daoPe.queryForAll();
        return todos;
    }
    public List<PedidoLog> getPedidosLog() throws SQLException {
        List<PedidoLog> todos = daoPelog.queryForAll();
        return todos;
    }
    public Pedido filtrarPedido(long id) throws SQLException {

        Pedido client = daoPe.queryForId(id);
        return client;
    }

    public void removePedido(int id) throws SQLException {
        daoPe.delete(daoPe.queryForEq("id", id));
    }

    public void EditarPedido(Pedido cat) throws SQLException {
        daoPe.updateId(cat, cat.getId());
    }

}
