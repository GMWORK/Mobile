package com.proyecto.gmwork.proyectoandroid.controller.utilidades;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;
import com.proyecto.gmwork.proyectoandroid.controller.parseJson;

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
        BufferedReader reader = null;
        HttpURLConnection conn = null;
        Content = new String[urls.length];
        try {
            for (int i = 0 ; i < urls.length ; i++) {
                URL url = new URL("http://192.168.1.3:8080/webService/webresources/"+urls[i]);
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
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            } finally {

            }
        }
    }

    public void SOS(String [] string) throws JSONException, SQLException, UnsupportedEncodingException {
        //categoria ,producto, usuario ,cliente,  pedido , productoPedido
        TreeMap<String, ArrayList> map = new TreeMap<String,ArrayList>();
        map.put("Categoria", parseJson.montarCategoria(string[0]));
        map.put("Productos", parseJson.montarProductos(string[1]));
        map.put("Usuario",parseJson.montarUsuarios(string[2]));
        map.put("Cliente",parseJson.montarClientes(string[3]));
        map.put("Pedido",parseJson.montarPedido(string[4]));
        map.put("PedidoProducto",parseJson.montarPedidoProducto(string[5]));
        map.put("HoraBajada", parseJson.montarHoraBajada(string[6]));
        per.guardarDatosBajados(map);
        //per.SOSUsuario(montarUsuarios(string[1]));
        //per.SOSCliente(montarClientes(string[2]));
        // per.SOSPedido(montarPedido(string[3]));
        // per.SOSProducto(montarProductos(string[4]));
        //this.bd.SOS(parseJson.SOS(string , bd));

    }
}
