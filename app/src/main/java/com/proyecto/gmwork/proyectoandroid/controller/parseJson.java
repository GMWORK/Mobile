package com.proyecto.gmwork.proyectoandroid.controller;

import com.j256.ormlite.field.types.DateTimeType;
import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by mateo on 13/05/15.
 */
public class parseJson {
    private Object url;


    public parseJson() {

    }

    public parseJson(Object url) {
        this.url = url;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }



    public static TreeMap<String,ArrayList> SOS(String [] string) throws JSONException {


        TreeMap<String , ArrayList> map =  new  TreeMap<String , ArrayList>();
        //Usuario
        map.put("Usuario",montarUsuarios(string[0]));
        map.put("Cliente",montarClientes(string[1]));
        return map;
    }

    private static ArrayList montarClientes(String s) throws JSONException {
        JSONArray json = new JSONArray(s);
        ArrayList clientes = new ArrayList();
        for (int i = 0 ; i< json.length(); i++) {
            JSONObject object = json.getJSONObject(i);
            clientes.add(new Cliente(object.getString("nif"), object.getString("nombre"), object.getString("apellidos"), Double.parseDouble(object.getString("longitud")), Double.parseDouble(object.getString("latitud")), object.getString("calle"), object.getString("poblacion"), new Date((Long.valueOf(object.getString("proximaVisita").replaceAll("T",":"))))));
        }
        return clientes;
    }

    private static ArrayList montarUsuarios(String string) throws JSONException {
        JSONArray json = new JSONArray(string);
        ArrayList usuarios = new ArrayList();
        for (int i = 0 ; i< json.length(); i++) {
            JSONObject object = json.getJSONObject(i);
            usuarios.add(new Usuario(object.getString("nif"), object.getString("nombre"), object.getString("apellidos"), object.getString("poblacion"), object.getString("calle"), Boolean.parseBoolean(object.getString("administrador")), object.getString("username"), object.getString("password")));

        }
        return usuarios;
    }
}
