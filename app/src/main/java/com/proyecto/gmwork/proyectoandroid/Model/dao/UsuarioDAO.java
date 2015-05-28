package com.proyecto.gmwork.proyectoandroid.Model.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.Model.Usuario;
import com.proyecto.gmwork.proyectoandroid.Model.UsuarioLog;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mateo on 19/05/15.
 */
public class UsuarioDAO {
    private Dao<Usuario, Long> daoUsu;
    private Dao<UsuarioLog, Long> daoUsulog;
    private OpenLiteHelper prodao;
    private Context con;

    public UsuarioDAO(Context con) {
        this.con = con;
        prodao = new OpenLiteHelper(con);
        try {
            daoUsu = prodao.getDAOUsuario();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> getUsuarios() {
        List<Usuario> todos = null;
        try {
            todos = daoUsu.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    public Usuario filtrarUsuario(String nif) {
        try {
            return daoUsu.queryForEq("nif", nif).get(0);
        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
    }

    public Usuario filtrarUsuario(int id) {
        Usuario usu = null;
        try {
            usu = daoUsu.queryForEq("id", id).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return usu;
    }

    public Usuario filtrarUser(String username) {
        Usuario usu = null;
        try {
            usu = daoUsu.queryForEq("username", username).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return usu;
    }


    public boolean addUsuario(Usuario cat) {
        try {
            if (cat.getId() == 0) {
                long id = daoUsu.queryForAll().get(daoUsu.queryForAll().size() - 1).getId();
                cat.setId(id);
            } else {
                daoUsu.createOrUpdate(cat);
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void removeUsuario(String nif) {

        try {
            daoUsu.delete(this.filtrarUsuario(nif));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void EditarProducto(Usuario usu) {
        try {
            daoUsu.updateId(usu, usu.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hacerLogin(Usuario cat) {
        try {
            Usuario usu = daoUsu.queryForEq("username", cat.getUsername()).get(0);

            if (usu.getPassword().equals(cat.getPassword())) {
                return true;

            } else {
                return false;
            }
        } catch (IndexOutOfBoundsException ex) {
            Log.i("Usuario", "Han introducido usuario erroneo");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void removeUsuario(long id) {
        try {
            daoUsu.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
