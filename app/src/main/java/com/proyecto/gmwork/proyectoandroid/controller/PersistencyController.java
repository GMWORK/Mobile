package com.proyecto.gmwork.proyectoandroid.controller;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.Model.Producto;
import com.proyecto.gmwork.proyectoandroid.Model.Usuario;
import com.proyecto.gmwork.proyectoandroid.controller.dao.ClienteDAOController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mateo on 30/04/15.
 */
public class PersistencyController {
    private PersistencyWebController perWeb;
    private ClienteDAOController cliDAO;
    private Context con;



    public PersistencyController (Context context) {
            con = context;
            cliDAO = new ClienteDAOController(context);

    }

    public boolean hacerLogin(String username, String password) throws SQLException {
        Cliente a = new Cliente();
        a.setNombre(username);
        a.setCalle(password);

        cliDAO.addCliente(a);
       if(cliDAO.filtrarCliente(a) != null){
            return true;
        }else{
            return false;
        }
    }

    public void guardarDatosBajados() {
    }

    public void removeProducto(String nombre) {
    }


    public Cliente mostrarClientes() {
        return null;
    }

    public Cliente filtrarCliente(String nombre) {
        return null;
    }

    public void editarCliente(String nif, int edad, String nombre, String apellidos, double latitud, double longitud, Date proximaVisita) {
    }

    public void crearCliente(String nif, int edad, String nombre, String apellidos, double latitud, double longitud, Date proximaVisita) {
    }

    public void crearPedido(Date fecha, Cliente cliente, ArrayList<Producto> productos) {
    }

    public void removePedido(long id, String client, Date fecha) {
    }

    public ArrayList<Pedido> mostrarPedido() {
        return null;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
