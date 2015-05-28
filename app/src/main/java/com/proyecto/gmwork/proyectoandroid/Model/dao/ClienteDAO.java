package com.proyecto.gmwork.proyectoandroid.Model.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.Model.ClienteLog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateo on 30/04/15.
 */
public class ClienteDAO {

    private Dao<Cliente, Long> daoCli;
    private Dao<ClienteLog, Long> daoClilog;
    private OpenLiteHelper clidao;

    public ClienteDAO(Context con) {
        clidao = new OpenLiteHelper(con);
        try {
            this.daoCli = clidao.getDAOCliente();
            daoClilog = clidao.getDAOClienteLog();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<ClienteLog> getLog() {
        List<ClienteLog> clienteslogs = new ArrayList<ClienteLog>();
        try {
            clienteslogs = daoClilog.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clienteslogs;
    }

    public void addCliente(Cliente cat) {
        boolean insert = false;
        while (insert != true) {
            try {
                if (cat.getId() == 0) {
                    long id = daoCli.queryForAll().get(daoCli.queryForAll().size() - 1).getId();
                    cat.setId(id);
                } else {
                    daoCli.createOrUpdate(cat);
                    insert = true;
                }
            } catch (SQLException ex) {
                Log.i("errorSQL", ex.getMessage());

            }
        }
    }

    public void crearCliente(Cliente cat) {


        try {
            daoCli.createOrUpdate(cat);

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public ArrayList<Cliente> getClientes(String usuario) {
        ArrayList<Cliente> todos = null;
        ArrayList<Cliente> clientesUsuario = new ArrayList<Cliente>();
        try {
            todos = (ArrayList<Cliente>) daoCli.queryForAll();


            Cliente cli = null;
            for (int i = 0; i < todos.size(); i++) {
                if (todos.get(i).getUsu().getUsername().equals(usuario.trim()) && !todos.get(i).isBaja()) {
                    clientesUsuario.add(todos.get(i));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientesUsuario;
    }

    public ArrayList<Cliente> getClientes() {
        ArrayList<Cliente> todos = null;
        try {
            todos = (ArrayList<Cliente>) daoCli.queryForAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    public void removeCliente(String nif) {
        Cliente cli = null;
        try {
            cli = clidao.getDAOCliente().queryForEq("nif", nif).get(0);
            cli.setBaja(true);
            clidao.getDAOCliente().update(cli);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cliente filtrarCliente(String nif) {
        Cliente cli = null;
        try {
            cli = clidao.getDAOCliente().queryForEq("nif", nif).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
        return cli;
    }

    public Cliente filtrarCliente(int id) {
        Cliente cli = null;
        try {
            try {
                cli = clidao.getDAOCliente().queryForEq("nif", id).get(0);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
        }
        return cli;
    }

    public ArrayList<Cliente> filtrarClienteCampo(String campo, String dato, String username) {
        ArrayList<Cliente> clientes = null;
        ArrayList<Cliente> filtro = new ArrayList<Cliente>();
        try {
            clientes = (ArrayList<Cliente>) daoCli.queryForEq(campo, dato);
            for (int i = 0; i < clientes.size(); i++) {
                if (clientes.get(i).getUsu().getUsername().equals(username.trim())) {
                    filtro.add(clientes.get(i));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
        return filtro;

    }

    public ArrayList<Cliente> filtrarCliente(String campo, String dato) {
        ArrayList<Cliente> clientes = null;
        try {
            clientes = (ArrayList<Cliente>) daoCli.queryForEq(campo, dato);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
        return clientes;


    }

    public void EditarCliente(Cliente client) {

        try {
            clidao.getDAOCliente().update(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cliente filtrarID(long id) {
        Cliente cli = null;
        try {
            cli = daoCli.queryForEq("id", id).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cli;
    }

    public void removeCliente(int id) {
        try {
            daoCli.deleteById((long) id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  /*  public List<Map<Cliente,ClienteLog>> getVista()  {
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
