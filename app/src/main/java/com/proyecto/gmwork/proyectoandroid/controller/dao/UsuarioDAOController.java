package com.proyecto.gmwork.proyectoandroid.controller.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.Model.Producto;
import com.proyecto.gmwork.proyectoandroid.Model.Usuario;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mateo on 19/05/15.
 */
public class UsuarioDAOController {
    private Dao<Usuario, Long> daoUsu;
    private OpenLiteHelper prodao;
    private Context con;
    public UsuarioDAOController(Context con) throws SQLException {
        this.con = con;
        prodao = new OpenLiteHelper(con);
        daoUsu = prodao.getDAOUsuario();
    }
    public List<Usuario> getUsuarios() throws SQLException {
        List<Usuario> todos = daoUsu.queryForAll();
        return todos;
    }
    public Usuario filtrarUsuario(String nif) throws SQLException {
        return daoUsu.queryForEq("nombre",nif).get(0);
    }
    public void addUsuario(Usuario cat) throws SQLException {
        daoUsu.create(cat);
    }
    public void removeUsuario(String nif) throws SQLException {

        daoUsu.delete(this.filtrarUsuario(nif));
    }
    public void EditarProducto(Usuario cat) throws SQLException {
        daoUsu.updateId(cat, cat.getId());
    }
}
