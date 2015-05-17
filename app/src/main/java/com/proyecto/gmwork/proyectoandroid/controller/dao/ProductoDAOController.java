package com.proyecto.gmwork.proyectoandroid.controller.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.Model.Producto;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Matthew on 18/05/2015.
 */
public class ProductoDAOController {
    private Dao<Producto, Long> daoPro;
    private OpenLiteHelper prodao;
    private Context con;
    public ProductoDAOController(Context con) throws SQLException {
        this.con = con;
        prodao = new OpenLiteHelper(con);
        daoPro = prodao.getDAOProducto();
    }
    public List<Producto> getPedidos() throws SQLException {
        List<Producto> todos = daoPro.queryForAll();
        return todos;
    }
}
