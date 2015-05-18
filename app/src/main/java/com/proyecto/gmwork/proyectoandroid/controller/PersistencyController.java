package com.proyecto.gmwork.proyectoandroid.controller;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.j256.ormlite.dao.ForeignCollection;
import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.Model.PedidoProducto;
import com.proyecto.gmwork.proyectoandroid.Model.Producto;
import com.proyecto.gmwork.proyectoandroid.Model.Usuario;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.controller.dao.ClienteDAOController;
import com.proyecto.gmwork.proyectoandroid.controller.dao.PedidoDAOController;
import com.proyecto.gmwork.proyectoandroid.controller.dao.ProductoDAOController;

import java.sql.Date;
import java.sql.SQLException;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mateo on 30/04/15.
 */
public class PersistencyController {
    private PersistencyWebController perWeb;
    private ClienteDAOController cliDAO;
    private ProductoDAOController proDAO;
    private PedidoDAOController peDAO;
    private OpenLiteHelper bd;
    private Context con;


    public PersistencyController(Context context) throws SQLException {
        con = context;
        bd = new OpenLiteHelper(con);
        cliDAO = new ClienteDAOController(context);
        peDAO = new PedidoDAOController(context);
        proDAO = new ProductoDAOController(context);
        perWeb = new PersistencyWebController(con, new OpenLiteHelper(con));
        perWeb.comprovarSOS(isNetworkAvailable());
    }

    public boolean hacerLogin(String username, String password) throws SQLException {
        Usuario user = new Usuario();
        user.setUsername(username);
        user.setPassword(password);

        // cliDAO.addCliente(a);
      /* if(cliDAO.filtrarCliente(a) != null){
            return true;
        }else{
            return false;
        }*/
        if (bd.hacerLogin(user)) {
            return true;
        } else {
            return false;
        }

    }

    public void guardarDatosBajados() {

    }

    public void removeCliente(String nif) throws SQLException {
        cliDAO.removeCliente(nif);

    }


    public List<Cliente> mostrarClientes() throws SQLException {
        List<Cliente> listDao = cliDAO.getClientes();
        return listDao;
    }

    public Cliente filtrarCliente(String nif) throws SQLException {
        return cliDAO.filtrarCliente(nif);
    }

    public void editarCliente(String beforenif, String newnif, String nombre, String apellidos, String poblacion, String calle, String proximaVisita) throws SQLException {
        Cliente client = cliDAO.filtrarCliente(beforenif);
        client.setNif(newnif);
        client.setNombre(nombre);
        client.setApellidos(apellidos);
        client.setProximaVisita(proximaVisita);
        client.setPoblacion(poblacion);
        client.setCalle(calle);
        cliDAO.EditarCliente(client);

    }

    public void crearCliente(String nif, String nombre, String apellidos, String poblacion, String calle, String proximaVisita) {
        Cliente cli = new Cliente(nif, nombre, apellidos, poblacion, calle, proximaVisita);
        cliDAO.addCliente(cli);
    }

    public void crearPedido(String fecha, Cliente cliente, ForeignCollection<PedidoProducto> productos) {
        Pedido pe = new Pedido();

        pe.setLiniaProducto(productos);
        pe.setCliente(cliente);
        pe.setFecha(fecha);
        peDAO.addPedido(pe);

    }


    public void removePedido(int id) throws SQLException {
        peDAO.removePedido(id);
    }

    public List<Pedido> mostrarPedido() throws SQLException {
        return peDAO.getPedidos();
    }

    public List<Producto> mostrarProducto() throws SQLException {
        return proDAO.getPedidos();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void ultimaComanda(Cliente client) {
        Iterator it = client.getPedido().iterator();
        Calendar max = Calendar.getInstance();
        while (it.hasNext()) {
            Pedido pe = (Pedido) it.next();
            String[] fecha = pe.getFecha().split("/");
            //max.set( Calendar.YEAR,fe);
        }
    }
}
