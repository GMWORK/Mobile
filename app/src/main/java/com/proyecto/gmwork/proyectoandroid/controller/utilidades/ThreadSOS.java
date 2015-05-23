package com.proyecto.gmwork.proyectoandroid.controller.utilidades;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.proyecto.gmwork.proyectoandroid.Model.Categoria;
import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.Horas;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.Model.PedidoProducto;
import com.proyecto.gmwork.proyectoandroid.Model.Producto;
import com.proyecto.gmwork.proyectoandroid.Model.Usuario;
import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;
import com.proyecto.gmwork.proyectoandroid.controller.parseJson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by mateo on 20/05/15.
 */
public class ThreadSOS  extends AsyncTask<String, Void, Void> {
    private ProgressDialog dialog;
String Error = "";
    String [] Content;
    PersistencyController per;
    public ThreadSOS(PersistencyController per,Context con) {
        dialog = new ProgressDialog(con);
        this.per = per;
    }

    @Override
    protected Void doInBackground(String... urls) {

        Content = new String[urls.length];
        try {

                BufferedReader reader = null;
                HttpURLConnection conn = null;
                URL url = null;
                Object serverResponseString = null;
                try {
                    for (int i = 0 ; i < urls.length ; i++) {
                    url = new URL("http://192.168.1.101:8080/WebGMWORK/webresources/"+urls[i]);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Accept", "application/json");

                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "");
                    }
                    Content[i] = sb.toString();
                }
                } catch (Exception e) {
                    Log.e("Error", "Error in doInBackground: " + e.getMessage());
                } finally {
                    try {
                        reader.close();
                        conn.disconnect();

                    } catch (Exception ex) {
                        Log.e("Error", "Error while closing buffer: " + ex.getMessage());
                    }
                }
                return null;




        } finally {


            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Please wait...");
        dialog.show();
        super.onPreExecute();
    }




    @Override
    protected void onPostExecute(Void unsed) {
        super.onPostExecute(unsed);
        dialog.dismiss();
        if (Error != null) {
            try {
                SOS(Content);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    public void SOS(String [] string) throws JSONException, SQLException, UnsupportedEncodingException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //categoria ,producto, usuario ,cliente,  pedido , productoPedido
        TreeMap<String, ArrayList> map = new TreeMap<String,ArrayList>();
        map.put("Categoria", parseJson.montarCategoria(string[0]));
        map.put("Productos", parseJson.montarProductos(string[1]));
        map.put("Usuario",parseJson.montarUsuarios(string[2]));
        map.put("Cliente",parseJson.montarClientes(string[3]));
        map.put("Pedido",parseJson.montarPedido(string[4]));
        map.put("PedidoProducto",parseJson.montarPedidoProducto(string[5]));
        map.put("HoraBajada", parseJson.montarHoraBajada(string[6]));
       /* map.put("Categoria", parseJsonSubida.JsontoObject(string[0],Categoria.class));
        map.put("Productos", parseJsonSubida.JsontoObject(string[1],Producto.class));
        map.put("Usuario",parseJsonSubida.JsontoObject(string[2],Usuario.class));
        map.put("Cliente",parseJsonSubida.JsontoObject(string[3],Cliente.class));
        map.put("Pedido",parseJsonSubida.JsontoObject(string[4],Pedido.class));
        map.put("PedidoProducto",parseJsonSubida.JsontoObject(string[5],PedidoProducto.class));
        map.put("HoraBajada", parseJsonSubida.JsontoObject(string[6],Horas.class));*/
        per.guardarDatosBajados(map);
        //per.SOSUsuario(montarUsuarios(string[1]));
        //per.SOSCliente(montarClientes(string[2]));
        // per.SOSPedido(montarPedido(string[3]));
        // per.SOSProducto(montarProductos(string[4]));
        //this.bd.SOS(parseJson.SOS(string , bd));

    }
}
