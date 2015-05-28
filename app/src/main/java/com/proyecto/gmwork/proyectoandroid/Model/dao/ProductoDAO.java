package com.proyecto.gmwork.proyectoandroid.Model.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.Model.Producto;
import com.proyecto.gmwork.proyectoandroid.Model.ProductoLog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 18/05/2015.
 */
public class ProductoDAO {
    private Dao<Producto, Long> daoPro;
    private Dao<ProductoLog, Long> daoProlog;
    private OpenLiteHelper prodao;
    private Context con;

    public ProductoDAO(Context con) {
        this.con = con;
        prodao = new OpenLiteHelper(con);
        try {
            daoPro = prodao.getDAOProducto();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Producto> getProductos() {
        List<Producto> todos = null;
        try {
            todos = daoPro.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    public List<Producto> getProductossBaja() {
        List<Producto> todos = null;
        List<Producto> pro = new ArrayList<Producto>();
        try {

            todos = daoPro.queryForAll();
            for (int i = 0; i < todos.size(); i++) {
                if (!todos.get(i).isInhabilitats()) {
                    pro.add(todos.get(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pro;
    }

    public Producto filtrarProducto(String nombre) {
        try {
            return daoPro.queryForEq("nombre", nombre).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Producto filtrarProducto(int id) {
        try {
            return daoPro.queryForEq("id", id).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addProducto(Producto cat) {
        try {
            if (cat.getId() == 0) {
                long id = daoPro.queryForAll().get(daoPro.queryForAll().size() - 1).getId();
                cat.setId(id);

            } else {
                daoPro.createOrUpdate(cat);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeProducto(String nombre) {

        try {
            daoPro.delete(this.filtrarProducto(nombre));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void EditarProducto(Producto cat) {
        try {
            daoPro.updateId(cat, cat.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeProducto(long id) {
        try {
            daoPro.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
