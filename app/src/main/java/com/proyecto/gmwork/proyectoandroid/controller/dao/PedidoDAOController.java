package com.proyecto.gmwork.proyectoandroid.controller.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.Model.PedidoLog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateo on 30/04/15.
 */
public class PedidoDAOController {
    private Dao<Pedido, Long> daoPe;
    private Dao<PedidoLog, Long> daoPelog;
    private OpenLiteHelper clidao;
    private Context con;

    public PedidoDAOController(Context con) {
        clidao = new OpenLiteHelper(con);
        try {
            this.daoPe = clidao.getDAOPedido();
            daoPelog = clidao.getDAOPedidoLog();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.con = con;
    }

    public void addPedido(Pedido cat) {
        try {
            daoPe.createOrUpdate(cat);
        } catch (SQLException ex) {
            Log.i("errorSQL", ex.getMessage());
        }
    }

    public List<Pedido> getPedidos(String username) {
        List<Pedido> todos = null;
        List<Pedido> pedidos = new ArrayList<Pedido>();
        try {
            todos = daoPe.queryForAll();
            for (int i = 0; i < todos.size(); i++) {
                if (todos.get(i).getCliente().getUsu().getUsername().equals(username) && !todos.get(i).isBaja()) {
                    pedidos.add(todos.get(i));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    public List<PedidoLog> getPedidosLog() {
        List<PedidoLog> todos = null;
        try {
            todos = daoPelog.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    public Pedido filtrarPedido(long id) {

        Pedido client = null;
        try {
            client = daoPe.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    public ArrayList<Pedido> filtrarPedidoLista(long id) {
        ArrayList<Pedido> ped = null;
        try {
            ped = (ArrayList<Pedido>) daoPe.queryForEq("id", id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clidao.close();

        }
        return ped;
    }


    public void removePedido(long id) {
        try {
            daoPe.delete(daoPe.queryForEq("id", id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void EditarPedido(Pedido cat) {
        try {
            daoPe.updateId(cat, cat.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
