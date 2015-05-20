package com.proyecto.gmwork.proyectoandroid.controller.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.Model.Categoria;
import com.proyecto.gmwork.proyectoandroid.Model.PedidoProducto;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mateo on 19/05/15.
 */
public class PedidoProductoDAOController {
    private Dao<PedidoProducto, Long> daoPePo;
    private OpenLiteHelper cliCat;

    public PedidoProductoDAOController(Context con) throws SQLException {
        cliCat = new OpenLiteHelper(con);
        this.daoPePo = cliCat.getDAOPedidoProducto();

    }
    public List<PedidoProducto> mostrarCategorias() throws SQLException {
        return daoPePo.queryForAll();
    }
    public PedidoProducto filtrarCategoria(String nombre) throws SQLException {
        return daoPePo.queryForEq("nombre",nombre).get(0);
    }
    public void addPedidoProducto(PedidoProducto cat) throws SQLException {
        daoPePo.createOrUpdate(cat);
    }
    public void removePedidoProducto(String nombre) throws SQLException {

        daoPePo.delete(this.filtrarCategoria(nombre));
    }
    public void EditarPedidoProducto(PedidoProducto cat) throws SQLException {
        daoPePo.updateId(cat, cat.getId());
    }
}
