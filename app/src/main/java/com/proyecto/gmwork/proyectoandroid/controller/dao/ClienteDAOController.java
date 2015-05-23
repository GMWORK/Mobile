package com.proyecto.gmwork.proyectoandroid.controller.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.Model.ClienteLog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    private Dao<ClienteLog, Long> daoClilog;
    private OpenLiteHelper clidao;

    public ClienteDAOController(Context con) throws SQLException {
        clidao = new OpenLiteHelper(con);
        this.daoCli = clidao.getDAOCliente();
        daoClilog = clidao.getDAOClienteLog();
    }
    public List<ClienteLog> getLog() throws SQLException {
        return daoClilog.queryForAll();
    }

    public void addCliente(Cliente cat) {
        try {
            daoCli.createOrUpdate(cat);

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
    public Cliente filtrarID(long id) throws SQLException {
        return daoCli.queryForEq("id",id).get(0);
    }
  /*  public List<Map<Cliente,ClienteLog>> getVista() throws SQLException {
       List <Map<Cliente,ClienteLog>>clientsvista = new ArrayList<Map<Cliente,ClienteLog>>();

        List<Cliente> clientes = getClientes();
        List<ClienteLog> logclientes = daoClilog.queryForAll();
        for (int i = 0 ; i< clientes.size();i++){
            for (int j = 0 ; j<logclientes.size();i++){
                if(clientes.get(i).getId() == logclientes.get(j).getIdCliente()){
                    getVista().add(new TreeMap<Cliente,ClienteLog>().put(clientes.get(i),logclientes.get(j)));
                }
            }
        }
    }*/
}
