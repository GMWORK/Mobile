package com.proyecto.gmwork.proyectoandroid.controller.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.Model.Categoria;
import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mateo on 18/05/15.
 */
public class CategoriaDAOController {
    private Dao<Categoria, Long> daoCat;
    private OpenLiteHelper cliCat;

    public CategoriaDAOController(Context con) throws SQLException {
        cliCat = new OpenLiteHelper(con);
        this.daoCat = cliCat.getDAOCategoria();

    }
    public List<Categoria> mostrarCategorias() throws SQLException {
        return daoCat.queryForAll();
    }
    public Categoria filtrarCategoria(String nombre) throws SQLException {
        return daoCat.queryForEq("nombre",nombre).get(0);
    }
    public void addCategoria(Categoria cat) throws SQLException {
         daoCat.createOrUpdate(cat);
    }
    public void removeCategoria(String nombre) throws SQLException {

        daoCat.delete(this.filtrarCategoria(nombre));
    }
    public void EditarCategoria(Categoria cat) throws SQLException {
        daoCat.update(cat);
    }

}
