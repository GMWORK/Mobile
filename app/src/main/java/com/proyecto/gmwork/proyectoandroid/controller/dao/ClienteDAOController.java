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

    public ArrayList<Cliente> getClientes() throws SQLException {
        ArrayList<Cliente> todos = (ArrayList<Cliente>) daoCli.queryForAll();
        return todos;
    }

    public Cliente filtrarCliente(Cliente cat) throws SQLException {
        Cliente client = clidao.getDAOCliente().queryForEq("nif",cat.getNif()).get(0);
        return client;
    }

    public void EditarCliente(Cliente cat) throws SQLException {
        Cliente client= clidao.getDAOCliente().queryForEq("nif", cat.getNif() ).get(0);

        clidao.getDAOCliente().update(client);


    }
}
