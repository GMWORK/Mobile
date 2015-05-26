package com.proyecto.gmwork.proyectoandroid.controller.utilidades;

import com.google.gson.Gson;
import com.proyecto.gmwork.proyectoandroid.Model.Categoria;
import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.Horas;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.Model.PedidoProducto;
import com.proyecto.gmwork.proyectoandroid.Model.Producto;
import com.proyecto.gmwork.proyectoandroid.Model.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mateo on 22/05/2015.
 */
public class parseJsonSubida {
    private Object url;


    public parseJsonSubida() {

    }

    public parseJsonSubida(Object url) {
        this.url = url;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }


    public static String ObjecttoJson(ArrayList objects) throws JSONException, ClassNotFoundException {

        JSONArray json = new JSONArray();

        Gson gson = new Gson();

        for (int i = 0; i < objects.size(); i++) {
            json.put(gson.toJson(gson.toJson(objects.get(i).toString())));

        }
        return json.toString();
    }

    public static ArrayList JsontoObject(String p, Class<?> clase) throws JSONException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ArrayList object = new ArrayList();
        JSONArray array = new JSONArray(p);
        Gson gson = new Gson();
        Class<?> dog = Class.forName(clase.getName());
        dog.newInstance();
        for (int i = 0; i < array.length(); i++) {
            JSONObject json = array.getJSONObject(i);
            System.out.println(dog.getClass());
            object.add(gson.fromJson(json.toString(),dog.getClass() ));
        }
        return object;


    }

}
