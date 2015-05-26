package com.proyecto.gmwork.proyectoandroid.controller;

import android.content.Context;
import android.os.AsyncTask;

import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.Horas;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.Model.Usuario;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.controller.utilidades.ThreadActualizarDownloadLogs;
import com.proyecto.gmwork.proyectoandroid.controller.utilidades.ThreadActualizarUpload;
import com.proyecto.gmwork.proyectoandroid.controller.utilidades.ThreadGetHoraServidor;
import com.proyecto.gmwork.proyectoandroid.controller.utilidades.ThreadSOS;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by mateo on 30/04/15.
 */
public class PersistencyWebController {
    private Context con;
    private boolean encontrarId;
    private PersistencyController per;

    public PersistencyWebController(Context con, PersistencyController per) {
        this.con = con;
        this.per = per;
    }

    public void comprovarSOS(boolean Network) {
        if (Network) {
            String[] usuari = {"categoria", "producto", "usuario", "cliente", "pedido", "productohaspedido", "date"};
            new ThreadSOS(per, con).execute(usuari);
        } else {
            per.dadesPrueba();
        }
    }

    String name = "";

    public TreeMap<String, ArrayList> actualizarDatos() throws InterruptedException, JSONException, UnsupportedEncodingException {
        String[] urls = new String[]{"categoriasadescargar", "productosadescargar", "usuarioadescargar", "clienteadescargar", "pedidosadescargar", "pedidoproductosadescargar", "date"};
        ThreadActualizarDownloadLogs actualizar = null;


        actualizar = new ThreadActualizarDownloadLogs(urls, con);
        Thread thread = new Thread(actualizar);
        thread.start();
        thread.join();


        return actualizar.getMap();
    }

    public void subirDatosLocales(TreeMap<String, List<String[]>> map) {

        try {
            ThreadActualizarUpload subir = new ThreadActualizarUpload(map, con);
            subir.execute();
        } finally {
            System.out.println("Subir Completado");
        }

    }

    private String[] Content;
    private String Error = "";
    // private ProgressDialog Dialog = new ProgressDialog(con);


    public void subirCliente(ArrayList<Cliente> usu) {

    }

    public void subirPedido(ArrayList<Pedido> usu) {
    }

    public TreeMap<String, ArrayList> bajarDatos() {
        return null;
    }


    /*public ArrayList<Cliente> bajarClientes() {




    }*/

    public ArrayList<Pedido> bajarPedidos() {
        return null;
    }

    public void activarActualizacionLocal() throws InterruptedException {
        String[] aDescargar = new String[]{"categoriasadescargar", "productosadescargar", "usuarioadescargar", "clienteadescargar", "pedidosadescargar", "pedidoproductosadescargar"};
     /*  Thread thread = new Thread(aDescargar,per,con));
        thread.start();
        thread.join();*/
    }

    public void subirInformacion(TreeMap<String, List<String[]>> map) {

    }

    public Horas getHora() {
        Horas hora = null;
        try {
            ThreadGetHoraServidor thread = new ThreadGetHoraServidor();
            Thread th = new Thread(thread);
            th.start();
            th.join();
            hora = thread.getHora().get(0);

        } catch (InterruptedException ex) {
            // Logger.getLogger(this).log(Level.SEVERE, null, ex);
        }
        return hora;
    }
    //TreeMap<String , ArrayList> map =  new  TreeMap<String , ArrayList>();
    //Usuario
        /*map.put("Categoria"),montarCategoria(String[0]);
        map.put("Producto",montarProductos(String[1]);
        map.put("Pedido",montarProductos(String[2]);
        map.put("Usuario",montarUsuarios(string[3]));
        map.put("Cliente",montarClientes(string[4]));
        return map;*/
}



