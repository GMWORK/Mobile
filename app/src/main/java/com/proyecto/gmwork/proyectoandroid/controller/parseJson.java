package com.proyecto.gmwork.proyectoandroid.controller;

import com.j256.ormlite.field.types.DateTimeType;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.Model.Categoria;
import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.Producto;
import com.proyecto.gmwork.proyectoandroid.Model.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
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


    public static ArrayList montarPedido(String  s) throws JSONException {;
        JSONArray json = new JSONArray(s);
        ArrayList pedido = new ArrayList();
        for (int i = 0 ; i< json.length(); i++) {
            JSONObject object = json.getJSONObject(i);

        }

        return pedido;
    }

    public static ArrayList montarCategoria(String s) throws JSONException {
        JSONArray json = new JSONArray(s);
        ArrayList categoria = new ArrayList();
        for (int i = 0 ; i< json.length(); i++) {
            JSONObject object = json.getJSONObject(i);
            //(String nombre, double precio, byte[] img, boolean inhabilitats, double descuento)
            //JSONObject object2 = object.getJSONObject("categoriaid");
            categoria.add(new Categoria(object.getString("nombre"),object.getDouble("descuento")));
        }
        return categoria;
    }
    public static ArrayList montarProductos(String s) throws JSONException, UnsupportedEncodingException {
        JSONArray json = new JSONArray(s);
        ArrayList productos = new ArrayList();
        for (int i = 0 ; i< json.length(); i++) {
            JSONObject object = json.getJSONObject(i);
            //(String nombre, double precio, byte[] img, boolean inhabilitats, double descuento)
            JSONObject object2 = object.getJSONObject("categoriaid");
            Producto pro = new Producto();
            Categoria cat = new Categoria();
            cat.setNombre(object2.getString("nombre"));
            pro.setPrecio(object.getDouble("precio"));
            pro.setCategoria(cat);
            pro.setDescuento(object.getDouble("descuento"));
            if(object.has("img")) {
                pro.setImg(object.getString("img").getBytes("UTF8"));
            }
            pro.setInhabilitats(Boolean.parseBoolean(object.getString("inhabilitats")));
            pro.setDescuento(Double.parseDouble(object.getString("descuento")));
            productos.add(pro);



        }
        return productos;
    }

    public static ArrayList montarClientes(String s) throws JSONException {
        JSONArray json = new JSONArray(s);
        ArrayList clientes = new ArrayList();
        for (int i = 0 ; i< json.length(); i++) {
            JSONObject object = json.getJSONObject(i);
            JSONObject object2 = object.getJSONObject("usuarioid");
            Usuario usu = new Usuario();
            Cliente cli = new Cliente();
            cli.setNif(object.getString("nif"));
            cli.setNombre(object.getString("nombre"));
            cli.setApellidos(object.getString("apellidos"));
            cli.setLatitud(object.getDouble("latitud"));
            cli.setLongitud(object.getDouble("longitud"));
            cli.setPoblacion(object.getString("poblacion"));
            cli.setProximaVisita(object.getString("proximaVisita"));
            cli.setCalle(object.getString("calle"));
            usu.setNif(object2.getString("nif"));
            usu.addClientes(cli);
        }
        return clientes;
    }

    public static ArrayList montarUsuarios(String string) throws JSONException {
        JSONArray json = new JSONArray(string);
        ArrayList usuarios = new ArrayList();
        for (int i = 0 ; i< json.length(); i++) {
            JSONObject object = json.getJSONObject(i);
            usuarios.add(new Usuario(object.getString("nif"), object.getString("nombre"), object.getString("apellidos"), object.getString("poblacion"), object.getString("calle"), Boolean.parseBoolean(object.getString("administrador")), object.getString("username"), object.getString("password")));
        }
        return usuarios;
    }

    public static ArrayList montarPedidoProducto(String S) {
        return null;
    }
}
