package com.proyecto.gmwork.proyectoandroid.controller.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.Model.Categoria;
import com.proyecto.gmwork.proyectoandroid.Model.CategoriaLog;
import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateo on 18/05/15.
 */
public class CategoriaDAOController {
    private Dao<Categoria, Long> daoCat;
    private Dao<CategoriaLog,Long> daoCatlog;
    private OpenLiteHelper cliCat;

    public CategoriaDAOController(Context con)  {

        try {
            cliCat = new OpenLiteHelper(con);
            this.daoCat = cliCat.getDAOCategoria();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public List<Categoria> mostrarCategorias()  {
        List<Categoria> cat  = null;
        try {
           cat = daoCat.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cat;
    }
    public Categoria filtrarCategoria(String nombre)  {
        Categoria cat = null;
        try {
            cat =  daoCat.queryForEq("nombre",nombre).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cat;
    }
    public Categoria filtrarCategoria(int id)  {
        Categoria cat  = null;
        try {
            cat =  daoCat.queryForEq("id",id).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cat;
    }
    public boolean addCategoria(Categoria cat)  {

        try {
            daoCat.createOrUpdate(cat);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean removeCategoria(String nombre)  {

        try {
            daoCat.delete(this.filtrarCategoria(nombre));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    public boolean EditarCategoria(Categoria cat)  {

        try {
            daoCat.update(cat);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
