package com.proyecto.gmwork.proyectoandroid.controller;


import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.Model.Producto;
import com.proyecto.gmwork.proyectoandroid.Model.Usuario;
import com.proyecto.gmwork.proyectoandroid.controller.dao.ClienteDAOController;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mateo on 30/04/15.
 */
public class PersistencyController {
    private PersistencyWebController perWeb;
    private ClienteDAOController cliDAO;

    public PersistencyController () {
        if(cliDAO ==null){
            cliDAO = new ClienteDAOController();
        }
    }

    public boolean hacerLogin(String username, String password) {
        Cliente a = new Cliente();
        a.setNombre(username);
        a.setCalle(password);

        cliDAO.addCliente(a);
      /*  if( perWeb.consultarUsuario()){
            return true;
        }else{
            return false;
        }*/
        return false;
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


}
