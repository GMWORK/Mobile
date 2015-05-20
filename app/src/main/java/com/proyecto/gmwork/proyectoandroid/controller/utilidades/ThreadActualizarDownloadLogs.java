package com.proyecto.gmwork.proyectoandroid.controller.utilidades;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by mateo on 20/05/15.
 */
public class ThreadActualizarDownloadLogs extends AsyncTask<String, Void, Void> {
    private ProgressDialog dialog;
    private String [] Content;
    private Calendar hour;
    private PersistencyController per;
    public ThreadActualizarDownloadLogs(PersistencyController per) {
        this.per = per;
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Please wait...");
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dialog.dismiss();
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
            for (int i = 0 ; i< Content.length ; i++){
                String content = Content[i];
                JSONArray json = new JSONArray(content);
                JSONObject object = json.getJSONObject(i);
                String fechamodificacion = object.getString("fecha");
                if(DateTime.parse(fechamodificacion).compareTo(DateTime.parse(per.getUltimaDescarga()))>0){
                switch (object.getString("Op")){
                    case "I":

                        break;

                }
                }
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

}
