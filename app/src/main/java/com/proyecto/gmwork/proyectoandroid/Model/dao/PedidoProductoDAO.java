package com.proyecto.gmwork.proyectoandroid.Model.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.Model.PedidoProducto;
import com.proyecto.gmwork.proyectoandroid.Model.PedidoProductoLog;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mateo on 19/05/15.
 */
public class PedidoProductoDAO {
    private Dao<PedidoProducto, Long> daoPePo;
    private Dao<PedidoProductoLog, Long> daoPePolog;
    private OpenLiteHelper cliCat;

    public PedidoProductoDAO(Context con) {

        try {
            cliCat = new OpenLiteHelper(con);
            this.daoPePo = cliCat.getDAOPedidoProducto();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<PedidoProducto> mostrarCategorias() {
        List<PedidoProducto> pePoList = null;
        try {
            pePoList = daoPePo.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pePoList;
    }

    public PedidoProducto filtrarCategoria(String nombre) {
        PedidoProducto pePo = null;
        try {
            pePo = daoPePo.queryForEq("nombre", nombre).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pePo;
    }


    public boolean addPedidoProducto(PedidoProducto cat) {

        // while (insert !=true) {
        try {
            //     if (cat.getId() == 0) {
            //       long id = daoPePo.queryForAll().get(daoPePo.queryForAll().size() - 1).getId();
            //     cat.setId(id);
            // } else {
            daoPePo.createOrUpdate(cat);
            //   insert = true;
            //}
            return true;
            //} catch (ArrayIndexOutOfBoundsException e) {
            //    cat.setId(1);

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    public boolean removePedidoProducto(String nombre) {
        PedidoProducto pePo = this.filtrarCategoria(nombre);
        try {
            daoPePo.delete(pePo);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean EditarPedidoProducto(PedidoProducto cat) {
        try {
            daoPePo.updateId(cat, cat.getId());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public PedidoProducto filtrarPedidoProductoPedido(long idPedido) {
        PedidoProducto pePo = null;
        try {
            pePo = daoPePo.queryForEq("id_pedido", idPedido).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pePo;
    }

    public boolean removePedidoProductodePedido(PedidoProducto pedProEs) {

        try {
            daoPePo.delete(pedProEs);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
