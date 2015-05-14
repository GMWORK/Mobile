package com.proyecto.gmwork.proyectoandroid.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.Model.Usuario;
import com.proyecto.gmwork.proyectoandroid.Model.mapping.OpenLiteHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by mateo on 30/04/15.
 */
public class PersistencyWebController  extends AsyncTask<String, Void, Void>{
    private   Context con;
    private boolean encontrarId;
    private OpenLiteHelper bd;

    public PersistencyWebController(Context con,OpenLiteHelper bd) {
        this.con = con;
        this.bd = bd;
    }
    public void comprovarSOS(){
        String[] usuari ={ "http://192.168.2.254:8080/webServiceJava/webresources/usuario" , "http://192.168.2.254:8080/webServiceJava/webresources/cliente" , "http://192.168.2.254:8080/webServiceJava/webresources/producto", "http://192.168.2.254:8080/webServiceJava/webresources/pedido"};
        this.execute(usuari);

    }
    String name = "";
    public boolean hacerLogin(Usuario usu) {

        //URL url;
        // try {
        // get URL content

                    /*URLConnection conn = url.openConnection();

                    // open the stream and put it into BufferedReader
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));

                    String inputLine;

                    //save to this Array
                    ArrayList array = new ArrayList();
                    while ((inputLine = br.readLine()) != null) {
                        array.add(inputLine);
                    }
                    for(int i=0;i<array.size();i++){
                        String o = array.get(i).toString();
                        parseJson js = new parseJson();
                       usuari = js.username(o);


                    }
                    br.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();

                //ParseJson js = new ParseJson();
                //js.pars();
            } catch (JSONException e) {
                    e.printStackTrace();
                }*/
        if(usu.getUsername().equals("")){
            return true;
        }else return false;


    }
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
    public void SOS(String [] string) throws JSONException, SQLException {
        this.bd.SOS(parseJson.SOS(string));
    }
}


