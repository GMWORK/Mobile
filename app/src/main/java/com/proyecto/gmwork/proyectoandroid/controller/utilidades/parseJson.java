package com.proyecto.gmwork.proyectoandroid.controller.utilidades;

import android.util.Log;
import android.widget.Toast;

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
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public static ArrayList montarPedido(String s) throws JSONException {

        JSONArray json = null;

        json = new JSONArray(s);
        ArrayList pedido = new ArrayList();
        int i = 0;
        try {
            while (i < json.length()) {
                JSONObject object = json.getJSONObject(i);
                JSONObject object2 = object.getJSONObject("clienteid");
                Pedido ped = new Pedido();
                Cliente client = new Cliente();
                ped.setId(object.getInt("id"));
                ped.setFechaEntrega(object.getString("fechaEntrega"));
                ped.setEstado(object.getString("estado"));
                client.setNif(object2.getString("nif"));
                ped.setBaja(object2.getBoolean("baja"));
                ped.setCliente(client);
                pedido.add(ped);
                i++;
            }
        } catch (Exception ex) {
            Log.i("Exception", ex.getMessage());
            i++;

        }
        return pedido;
    }


    public static ArrayList montarCategoria(String s) throws JSONException {
        JSONArray json = new JSONArray(s);
        ArrayList categoria = new ArrayList();
        int i = 0;
        try {
            while (i < json.length()) {
                JSONObject object = json.getJSONObject(i);
                Categoria cat = new Categoria();
                cat.setId(object.getInt("id"));
                cat.setNombre(object.getString("nombre"));
                cat.setDescuento(object.getDouble("descuento"));
                categoria.add(cat);
                i++;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            i++;
        }
        return categoria;
    }


    public static ArrayList montarProductos(String s) throws JSONException {
        JSONArray json = new JSONArray(s);
        ArrayList productos = new ArrayList();
        int i = 0;
        try {
            while (i < json.length()) {
                JSONObject object = json.getJSONObject(i);

                JSONObject object2 = object.getJSONObject("categoriaid");
                Producto pro = new Producto();
                Categoria cat = new Categoria();
                cat.setNombre(object2.getString("nombre"));
                pro.setPrecio(object.getDouble("precio"));
                pro.setNombre(object.getString("nombre"));
                pro.setId(object.getInt("id"));

                pro.setCategoria(cat);
                pro.setDescuento(object.getDouble("descuento"));
                if (object.has("img")) {
                    pro.setImg(object.getString("img"));
                }
                pro.setInhabilitats(object.getBoolean("inhabilitats"));
                pro.setDescuento(object.getDouble("descuento"));
                productos.add(pro);
                i++;
            }
        } catch (JSONException ex) {
            System.out.println();
            i++;
        }
        return productos;
    }

    public static ArrayList montarClientes(String s) throws JSONException {
        JSONArray json = new JSONArray(s);
        ArrayList clientes = new ArrayList();
        int i = 0;
        try {
            while (i < json.length()) {
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
                cli.setId(object.getInt("id"));
                cli.setBaja(object.getBoolean("baja"));
                if (object.has("img")) {
                    cli.setImg(object.getString("img"));
                }
                usu.setNif(object2.getString("nif"));
                cli.setUsu(usu);
                clientes.add(cli);
                i++;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            i++;
        }
        return clientes;
    }

    public static ArrayList montarUsuarios(String string) throws JSONException {
        ArrayList usuarios = new ArrayList();

        JSONArray json = new JSONArray(string);
        usuarios = new ArrayList();
        int i = 0;
        try {
            while (i < json.length()) {
                JSONObject object = json.getJSONObject(i);
                Usuario usu = new Usuario();
                usu.setNif(object.getString("nif"));
                usu.setId(object.getInt("id"));
                usu.setNombre(object.getString("nombre"));
                usu.setApellidos(object.getString("apellidos"));
                usu.setPoblacion(object.getString("poblacion"));
                usu.setCalle(object.getString("calle"));
                usu.setAdministrador(object.getBoolean("administrador"));
                usu.setUsername(object.getString("username"));
                usu.setPassword(object.getString("password"));
                usu.setBaja(object.getBoolean("baja"));
                usuarios.add(usu);
                i++;
            }

        } catch (JSONException ex) {
            Logger.getLogger(parseJson.class.getName()).log(Level.SEVERE, null, ex);
            i++;
        } finally {
            return usuarios;
        }
    }

    public static ArrayList montarPedidoProducto(String string) throws JSONException {

        JSONArray json = new JSONArray(string);
        ArrayList pedidoProducto = new ArrayList();
        int i = 0;
        try {
            while (i < json.length()) {
                JSONObject object = json.getJSONObject(i);
                JSONObject object2 = object.getJSONObject("pedido");
                JSONObject object3 = object.getJSONObject("producto");
                PedidoProducto pePro = new PedidoProducto();
                Pedido ped = new Pedido();
                ped.setTotal(object2.getDouble("total"));
                ped.setFechaEntrega(object2.getString("fechaEntrega"));
                ped.setEstado(object2.getString("estado"));
                ped.setId(object2.getInt("id"));
                Producto pro = new Producto();
                pro.setNombre(object3.getString("nombre"));
                pePro.setCantidad(object.getInt("cantidad"));

                pePro.setProducto(pro);

                pePro.setPedido(ped);
                pedidoProducto.add(pePro);
                i++;
            }
        } catch (Exception ex) {
            i++;
        }

        return pedidoProducto;
    }

    public static ArrayList montarHoraBajada(String string) throws JSONException {
        JSONObject json = new JSONObject(string);
        ArrayList data = new ArrayList();
        int i = 0;
        try {
            while (i < json.length()) {

                Horas hora = new Horas();
                hora.setFecha(json.getString("value"));
                data.add(hora);
                i++;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return data;
    }
}
