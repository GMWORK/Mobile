package com.proyecto.gmwork.proyectoandroid.Model.dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.Model.Categoria;
import com.proyecto.gmwork.proyectoandroid.Model.CategoriaLog;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mateo on 18/05/15.
 * Classe que se encarga de La comunicacion con la base de datos
 */
public class CategoriaDAO {

    private Dao<Categoria, Long> daoCat;
    private Dao<CategoriaLog, Long> daoCatlog;
    private OpenLiteHelper cliCat;
    private Context con;

    public CategoriaDAO(Context con) {
        this.con = con;
        try {
            cliCat = new OpenLiteHelper(con);
            this.daoCat = cliCat.getDAOCategoria();
        } catch (SQLException e) {
            Toast.makeText(con,e.getMessage(),Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(con,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    /**
     *
     * */
    public List<Categoria> mostrarCategorias() {
        List<Categoria> cat = null;
        try {
            cat = daoCat.queryForAll();
        } catch (SQLException e) {
            Toast.makeText(con,e.getMessage(),Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(con,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return cat;
    }

    public Categoria filtrarCategoria(String nombre) {
        Categoria cat = null;
        try {
            cat = daoCat.queryForEq("nombre", nombre).get(0);
        } catch (SQLException e) {
            Toast.makeText(con,e.getMessage(),Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(con,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return cat;
    }

    public Categoria filtrarCategoria(int id) {
        Categoria cat = null;
        try {
            cat = daoCat.queryForEq("id", id).get(0);
        } catch (SQLException e) {
            Toast.makeText(con,e.getMessage(),Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(con,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return cat;
    }

    public boolean addCategoria(Categoria cat) {

        try {
            //     if (cat.getId() == 0) {*/
            //   long id = daoCat.queryForAll().get(daoCat.queryForAll().size() - 1).getId();
            //     cat.setId(id);
            //} else {
            daoCat.createOrUpdate(cat);
            // }
            //
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            Toast.makeText(con,e.getMessage(),Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean removeCategoria(String nombre) {

        try {
            daoCat.delete(this.filtrarCategoria(nombre));
            return true;
        } catch (SQLException e) {
            Toast.makeText(con,e.getMessage(),Toast.LENGTH_SHORT).show();
            return false;
        } catch (Exception e) {
            Toast.makeText(con,e.getMessage(),Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public boolean EditarCategoria(Categoria cat) {

        try {
            daoCat.update(cat);
            return true;
        } catch (SQLException e) {
            Toast.makeText(con,e.getMessage(),Toast.LENGTH_SHORT).show();
            return false;
        }  catch (Exception e) {
            Toast.makeText(con,e.getMessage(),Toast.LENGTH_SHORT).show();
        return false;
    }
    }

    public void removeCategoria(long id) {
        try {
            daoCat.deleteById(id);
        } catch (SQLException e) {
            Toast.makeText(con,e.getMessage(),Toast.LENGTH_SHORT).show();
        }catch (Exception e) {

            Toast.makeText(con,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
