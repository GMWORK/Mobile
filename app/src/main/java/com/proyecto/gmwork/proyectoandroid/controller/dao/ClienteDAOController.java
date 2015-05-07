package com.proyecto.gmwork.proyectoandroid.controller.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.mapping.OpenLiteHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateo on 30/04/15.
 */
public class ClienteDAOController {
    /**
     * Returns an instance of the data access object
     *
     * @return
     * @throws SQLException
     */
    private Dao<Cliente, Long> daoCli;
    private OpenLiteHelper clidao;
    public ClienteDAOController(Context con) {
            clidao = new OpenLiteHelper(con);


    }


    public void addCliente(Cliente cat) {
        try {
            clidao.getDAOCliente().create(cat);

        } catch (SQLException ex) {
            Log.i("errorSQL", ex.getMessage());
        }
    }

    public List<Cliente> getClientes() throws SQLException {
        List<Cliente> todos = daoCli.queryForAll();
        return todos;
    }

    public Cliente filtrarCliente(Cliente cat) throws SQLException {
        Cliente a = clidao.getDAOCliente().queryForEq("nombre",cat.getNombre()).get(0);
        return a;
    }

    public void EditarCliente(Cliente cat) {
    }
}
