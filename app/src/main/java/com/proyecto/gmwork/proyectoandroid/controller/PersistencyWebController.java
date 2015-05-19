package com.proyecto.gmwork.proyectoandroid.controller;

import android.content.Context;
import android.os.AsyncTask;

import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.Model.Usuario;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;

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
import java.util.TreeMap;

/**
 * Created by mateo on 30/04/15.
 */
public class PersistencyWebController  extends AsyncTask<String, Void, Void>{
    private   Context con;
    private boolean encontrarId;
    private PersistencyController per;

    public PersistencyWebController(Context con,PersistencyController per) throws SQLException {
        this.con = con;
        this.per = per;
    }
    public void comprovarSOS(boolean Network) throws SQLException {
        if(Network){

        String[] usuari ={ "http://192.168.1.187:8080/WebservicesJava2/webresources/categoria" , "http://192.168.1.187:8080/WebservicesJava2/webresources/producto" , "http://192.168.1.187:8080/WebservicesJava2/webresources/usuario", "http://192.168.1.187:8080/WebservicesJava2/webresources/cliente","http://192.168.1.187:8080/WebservicesJava2/webresources/pedido","http://192.168.1.187:8080/WebservicesJava2/webresources/productoPedido"};
        this.execute(usuari);
        }else{
            per.dadesPrueba();
        }
    }
    String name = "";

    private String []Content;
    private String Error = "";
    // private ProgressDialog Dialog = new ProgressDialog(con);


    @Override
    protected Void doInBackground(String... urls) {
        BufferedReader reader = null;
        HttpURLConnection conn = null;
        Content = new String[urls.length];
        try {
            for (int i = 0 ; i < urls.length ; i++) {
                URL url = new URL(urls[i]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "");
                }
                 Content[i] = sb.toString();
            }
        } catch (MalformedURLException ex) {
            ex.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                reader.close();
                conn.disconnect();

            } catch (Exception ex) {

            }
            return null;
        }
    }



    @Override
    protected void onPreExecute() {
        /*Dialog.setMessage("Please wait...");
        Dialog.show();*/
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void unsed) {
        super.onPostExecute(unsed);
        //Dialog.dismiss();
        if (Error != null) {
            try {
                SOS(Content);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            } finally {

            }
        }
    }

    public void subirCliente(ArrayList<Cliente> usu) {
    }

    public void subirPedido(ArrayList<Pedido> usu) {
    }

    public TreeMap<String, ArrayList> bajarDatos() {
        return null;
    }


    public ArrayList<Cliente> bajarClientes() {
        return null;
    }

    public ArrayList<Pedido> bajarPedidos() {
        return null;
    }

    public boolean consultarUsuario() {
        return false;
    }
    public void SOS(String [] string) throws JSONException, SQLException, UnsupportedEncodingException {
        //categoria ,producto, usuario ,cliente,  pedido , productoPedido
        TreeMap<String, ArrayList> map = new TreeMap<String,ArrayList>();
        map.put("Categoria", parseJson.montarCategoria(string[0]));
        map.put("Productos", parseJson.montarProductos(string[1]));
        map.put("Usuario",parseJson.montarUsuarios(string[2]));
        //map.put("Cliente",parseJson.montarClientes(string[3]));
        //map.put("Pedido",parseJson.montarPedido(string[4]));
       // map.put("PedidoProducto",parseJson.montarPedidoProducto(string[5]));
       // map.put("PedidoProducto"),parseJson.mont)
        per.guardarDatosBajados(map);
        //per.SOSUsuario(montarUsuarios(string[1]));
        //per.SOSCliente(montarClientes(string[2]));
       // per.SOSPedido(montarPedido(string[3]));
       // per.SOSProducto(montarProductos(string[4]));
        //this.bd.SOS(parseJson.SOS(string , bd));

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



