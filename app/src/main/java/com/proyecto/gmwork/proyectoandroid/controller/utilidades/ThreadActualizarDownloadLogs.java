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
public class ThreadActualizarDownloadLogs extends AsyncTask<String[],  Void, TreeMap<String, ArrayList>> {
    private ProgressDialog dialog;
    private String[] Content;
    private String[] string;
    private TreeMap<String, ArrayList> map;
    private Context con;
    PersistencyController per;
    public ThreadActualizarDownloadLogs(PersistencyController per, Context con) {
        this.con = con;
        this.string = string;
        this.per = per;
        map = new TreeMap<String,ArrayList>();
    }


    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(con);
        dialog.setMessage("Please wait...");
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(TreeMap<String, ArrayList> stringArrayListTreeMap) {
        per.actualizarDatosLocales(map);
        super.onPostExecute(stringArrayListTreeMap);


    }

    @Override
    protected TreeMap<String, ArrayList> doInBackground(String[]... params) {

        BufferedReader reader = null;
        HttpURLConnection conn = null;
        try {

            Content = new String[params[0].length];
            for (int i = 0; i < params[0].length; i++) {
                URL url = new URL("http://10.0.2.2:8080/WebGMWORK/webresources/" + params[0][i]);
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

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        } finally {

            try {
                map = new TreeMap<String, ArrayList>();
                map.put("Categoria", parseJsonVistas.parseCategoriaLog(Content[0]));
                map.put("Productos", parseJsonVistas.parseProductoVista(Content[1]));
                map.put("Usuario", parseJsonVistas.parseUsuarioLog(Content[2]));
                map.put("Cliente", parseJsonVistas.parseClienteLog(Content[3]));
                map.put("Pedido", parseJsonVistas.parsePedido(Content[4]));
                map.put("PedidoProducto", parseJsonVistas.parsePedidoproductoLog(Content[5]));
                map.put("HoraBajada", parseJson.montarHoraBajada(Content[6]));
                reader.close();
                conn.disconnect();

            } catch (Exception ex) {

            }
            return map;

        }
    }
}
