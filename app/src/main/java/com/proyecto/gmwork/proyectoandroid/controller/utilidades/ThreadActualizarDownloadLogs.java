package com.proyecto.gmwork.proyectoandroid.controller.utilidades;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by mateo on 20/05/15.
 */
public class ThreadActualizarDownloadLogs implements Runnable {
    private ProgressDialog dialog;
    private String[] Content;
    private PersistencyController per;
    private String[] string;
    private TreeMap<String, ArrayList> map;

    public ThreadActualizarDownloadLogs(String[] string, Context con) {
        dialog = new ProgressDialog(con);
        this.string = string;


    }


    public TreeMap<String, ArrayList> getMap() {
        return map;
    }


    public void doOperation() throws JSONException, SQLException {

        map = new TreeMap<String, ArrayList>();
        map.put("Categoria", parseJsonVistas.parseCategoriaLog(Content[0]));
        map.put("Productos", parseJsonVistas.parseProductoVista(Content[1]));
        map.put("Usuario", parseJsonVistas.parseUsuarioLog(Content[2]));
        map.put("Cliente", parseJsonVistas.parseClienteLog(Content[3]));
        map.put("Pedido", parseJsonVistas.parsePedido(Content[4]));
        map.put("PedidoProducto", parseJsonVistas.parsePedidoproductoLog(Content[5]));
        map.put("HoraBajada", parseJson.montarHoraBajada(Content[6]));


    }


    /*@Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        try {
            doOperation();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dialog.dismiss();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog.setMessage("Please wait...");
        dialog.show();
    }*/
/*
    @Override
    protected Void doInBackground(String... params) {
        BufferedReader reader = null;
        HttpURLConnection conn = null;
        try {

            Content = new String[string.length];
            for (int i = 0; i < string.length; i++) {
                URL url = new URL("http://192.168.1.101:8080/WebGMWORK/webresources/" + string[i]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setRequestProperty("Accept", "application/json");
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
                doOperation();
                reader.close();
                conn.disconnect();

            } catch (Exception ex) {

            }


        }
        return null;
    }*/

    @Override
    public void run() {
        BufferedReader reader = null;
        HttpURLConnection conn = null;
        try {

            Content = new String[string.length];
            for (int i = 0; i < string.length; i++) {
                URL url = new URL("http://192.168.1.101:8080/WebGMWORK/webresources/" + string[i]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setRequestProperty("Accept", "application/json");
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
                doOperation();
                reader.close();
                conn.disconnect();

            } catch (Exception ex) {

            }


        }
    }
}
