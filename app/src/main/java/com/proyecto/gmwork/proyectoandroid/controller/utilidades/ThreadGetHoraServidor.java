package com.proyecto.gmwork.proyectoandroid.controller.utilidades;

import com.proyecto.gmwork.proyectoandroid.Model.Horas;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Mateo on 25/05/2015.
 */
public class ThreadGetHoraServidor implements Runnable {
    @Override
    public void run() {
        BufferedReader reader = null;
        HttpURLConnection conn = null;
        URL url = null;
        Object serverResponseString = null;

        try {//10.0.2.2
            url = new URL("http://10.0.2.2:8080/WebGMWORK/webresources/date");

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/json");

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "");
            }
            hora = sb.toString();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ThreadGetHoraServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                reader.close();
                conn.disconnect();

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    private String hora;

    public ArrayList<Horas> getHora() {
        ArrayList array = new ArrayList();
        try {
            array.add(parseJson.montarHoraBajada(hora).get(0));

        } catch (JSONException ex) {
            Logger.getLogger(ThreadGetHoraServidor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return array;
        }
    }
}
