package com.proyecto.gmwork.proyectoandroid.controller;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.dao.ForeignCollection;
import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.Model.PedidoProducto;
import com.proyecto.gmwork.proyectoandroid.Model.Producto;
import com.proyecto.gmwork.proyectoandroid.Model.Usuario;
import com.proyecto.gmwork.proyectoandroid.Model.mapping.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.controller.dao.ClienteDAOController;
import com.proyecto.gmwork.proyectoandroid.controller.dao.PedidoDAOController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mateo on 30/04/15.
 */
public class PersistencyController {
    private PersistencyWebController perWeb;
    private ClienteDAOController cliDAO;
    private PedidoDAOController peDAO;
    private Context con;



    public PersistencyController (Context context) throws SQLException {
            con = context;
            cliDAO = new ClienteDAOController(context);
        peDAO= new PedidoDAOController(context);
        perWeb = new PersistencyWebController(con,new OpenLiteHelper(con));
        perWeb.comprovarSOS();
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
        if(perWeb.hacerLogin(user)){
            return true;
        }else{
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
        Cliente cli = new Cliente();
        cli.setNif(nif);
        cliDAO.filtrarCliente(nif);
        return cli;
    }

    public void editarCliente(String nif, int edad, String nombre, String apellidos, String  poblacion, String calle, Date proximaVisita) throws SQLException {
        Cliente client = cliDAO.filtrarCliente(nif);
        client.setNombre(nombre);
        client.setApellidos(apellidos);
        client.setProximaVisita(proximaVisita);
        client.setPoblacion(poblacion);
        client.setCalle(calle);
        cliDAO.EditarCliente(client);

    }

    public void crearCliente(String nif, String nombre, String apellidos,String poblacion, String calle, Date proximaVisita) {
        Cliente cli = new  Cliente(nif,nombre,apellidos,poblacion,calle,proximaVisita);
        cliDAO.addCliente(cli);
    }

    public void crearPedido(Date fecha, Cliente cliente, ForeignCollection<PedidoProducto> productos) {
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
