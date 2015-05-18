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



    public static void SOS(String [] string,OpenLiteHelper bd) throws JSONException, SQLException, UnsupportedEncodingException {

//categoria , usuario ,cliente, producto , pedido , productoPedido
        bd.SOSCategoria(montarCategoria(string[0]));
        bd.SOSUsuario(montarUsuarios(string[1]));
        bd.SOSCliente(montarClientes(string[2]));
        bd.SOSPedido(montarPedido(string[3]));
        bd.SOSProducto(montarProductos(string[4]));







        //TreeMap<String , ArrayList> map =  new  TreeMap<String , ArrayList>();
        //Usuario
        /*map.put("Categoria"),montarCategoria(String[0]);
        map.put("Producto",montarProductos(String[1]);
        map.put("Pedido",montarProductos(String[2]);
        map.put("Usuario",montarUsuarios(string[3]));
        map.put("Cliente",montarClientes(string[4]));
        return map;*/
    }

    private static ArrayList montarPedido(String  s) throws JSONException {;
        JSONArray json = new JSONArray(s);
        ArrayList pedido = new ArrayList();
        for (int i = 0 ; i< json.length(); i++) {
            JSONObject object = json.getJSONObject(i);

        }

        return pedido;
    }

    private static ArrayList montarCategoria(String s) throws JSONException {
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
    private static ArrayList montarProductos(String s) throws JSONException, UnsupportedEncodingException {
        JSONArray json = new JSONArray(s);
        ArrayList productos = new ArrayList();
        for (int i = 0 ; i< json.length(); i++) {
            JSONObject object = json.getJSONObject(i);
            //(String nombre, double precio, byte[] img, boolean inhabilitats, double descuento)
            JSONObject object2 = object.getJSONObject("categoriaid");
            Producto pro = new Producto();
            Categoria cat = new Categoria();
            cat.setNombre(object2.getString("nombre"));
            cat.setDescuento(Double.parseDouble(object2.getString("descuento")));
            pro.setCategoria(cat);
            pro.setNombre(object.getString("nombre"));
            pro.setDescuento(object.getDouble("precio"));
            if(object.has("img")) {
                pro.setImg(object.getString("img").getBytes("UTF8"));
            }
            pro.setInhabilitats(Boolean.parseBoolean(object.getString("inhabilitats")));
            pro.setDescuento(Double.parseDouble(object.getString("descuento")));



        }
        return productos;
    }

    private static ArrayList montarClientes(String s) throws JSONException {
        JSONArray json = new JSONArray(s);
        ArrayList clientes = new ArrayList();
        for (int i = 0 ; i< json.length(); i++) {
            JSONObject object = json.getJSONObject(i);
            clientes.add(new Cliente(object.getString("nif"), object.getString("nombre"), object.getString("apellidos"), Double.parseDouble(object.getString("longitud")), Double.parseDouble(object.getString("latitud")), object.getString("calle"), object.getString("poblacion"), object.getString("proximaVisita")));
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
