package com.proyecto.gmwork.proyectoandroid.controller.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;

import java.sql.SQLException;
import java.util.ArrayList;

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

    public ClienteDAOController(Context con) throws SQLException {
        clidao = new OpenLiteHelper(con);
        this.daoCli = clidao.getDAOCliente();
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

    public void removeCliente(String nif) throws SQLException {

        clidao.getDAOCliente().delete(clidao.getDAOCliente().queryForEq("nif", nif));
    }

    public Cliente filtrarCliente(String nif) throws SQLException {
        Cliente client = clidao.getDAOCliente().queryForEq("nif", nif).get(0);
        return client;
    }

    public void EditarCliente(Cliente client) throws SQLException {
        clidao.getDAOCliente().update(client);
    }


}
