package com.proyecto.gmwork.proyectoandroid.controller.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.Model.PedidoProducto;
import com.proyecto.gmwork.proyectoandroid.Model.Producto;
import com.proyecto.gmwork.proyectoandroid.Model.ProductoLog;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Matthew on 18/05/2015.
 */
public class ProductoDAOController {
    private Dao<Producto, Long> daoPro;
    private Dao<ProductoLog, Long> daoProlog;
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
    public Producto filtrarProducto(String nombre) throws SQLException {
        return daoPro.queryForEq("nombre",nombre).get(0);
    }
    public void addProducto(Producto cat) throws SQLException {
        daoPro.createOrUpdate(cat);
    }
    public void removeProducto(String nombre) throws SQLException {

        daoPro.delete(this.filtrarProducto(nombre));
    }
    public void EditarProducto(Producto cat) throws SQLException {
        daoPro.updateId(cat, cat.getId());
    }
}
