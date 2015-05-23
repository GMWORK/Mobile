package com.proyecto.gmwork.proyectoandroid.controller;

import android.content.Context;
import android.os.AsyncTask;

import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.Model.Usuario;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.controller.utilidades.ThreadActualizarDownloadLogs;
import com.proyecto.gmwork.proyectoandroid.controller.utilidades.ThreadActualizarUpload;
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

/**
 * Created by mateo on 30/04/15.
 */
public class PersistencyWebController {
    private   Context con;
    private boolean encontrarId;
    private PersistencyController per;

    public PersistencyWebController(Context con,PersistencyController per) throws SQLException {
        this.con = con;
        this.per = per;
    }
    public void comprovarSOS(boolean Network) throws SQLException {
        if(Network){
        String[] usuari ={ "categoria" , "producto" ,"usuario", "cliente","pedido","productohaspedido", "date"};
        new ThreadSOS(per,con).execute(usuari);
        }else{
            per.dadesPrueba();
        }
    }
    String name = "";

    private String []Content;
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
        String [] aDescargar = new String[]{ "categoriasadescargar" ,"productosadescargar" , "usuarioadescargar","clienteadescargar" , "pedidosadescargar", "pedidoproductosadescargar" };
     /*  Thread thread = new Thread(aDescargar,per,con));
        thread.start();
        thread.join();*/
    }

    public void subirInformacion(TreeMap<String, List<String[]>> map) {

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



