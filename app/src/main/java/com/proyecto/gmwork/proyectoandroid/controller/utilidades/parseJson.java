package com.proyecto.gmwork.proyectoandroid.controller.utilidades;

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

            JSONArray json = new JSONArray(s);
            ArrayList pedido = new ArrayList();
            for (int i = 0; i < json.length(); i++) {

                JSONObject object = json.getJSONObject(i);
                JSONObject object2 = object.getJSONObject("clienteid");

                Pedido ped = new Pedido();
                Cliente client = new Cliente();
                ped.setFechaEntrega(object.getString("fechaEntrega"));
                ped.setEstado(object.getString("estado"));
                client.setNif(object2.getString("nif"));
                ped.setBaja(object2.getBoolean("baja"));
                ped.setCliente(client);
                pedido.add(ped);

            }

            return pedido;
        }

        public static ArrayList montarCategoria(String s) throws JSONException {
            JSONArray json = new JSONArray(s);
            ArrayList categoria = new ArrayList();
            for (int i = 0; i < json.length(); i++) {
                JSONObject object = json.getJSONObject(i);

                categoria.add(new Categoria(object.getString("nombre"), object.getDouble("descuento")));
            }
            return categoria;
        }

        public static ArrayList montarProductos(String s) throws JSONException, UnsupportedEncodingException {
            JSONArray json = new JSONArray(s);
            ArrayList productos = new ArrayList();
            for (int i = 0; i < json.length(); i++) {
                JSONObject object = json.getJSONObject(i);

                JSONObject object2 = object.getJSONObject("categoriaid");
                Producto pro = new Producto();
                Categoria cat = new Categoria();
                cat.setNombre(object2.getString("nombre"));
                pro.setPrecio(object.getDouble("precio"));
                pro.setNombre(object.getString("nombre"));


                pro.setCategoria(cat);
                pro.setDescuento(object.getDouble("descuento"));
                if (object.has("img")) {
                    pro.setImg(object.getString("img").getBytes("UTF8"));
                }
                pro.setInhabilitats(object.getBoolean("inhabilitats"));
                pro.setDescuento(object.getDouble("descuento"));
                productos.add(pro);

            }
            return productos;
        }

        public static ArrayList montarClientes(String s) throws JSONException {
            JSONArray json = new JSONArray(s);
            ArrayList clientes = new ArrayList();
            for (int i = 0; i < json.length(); i++) {
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
                cli.setBaja(object.getBoolean("baja"));
                usu.setNif(object2.getString("nif"));
                cli.setUsu(usu);
                clientes.add(cli);
            }
            return clientes;
        }

        public static ArrayList montarUsuarios(String string) {
            ArrayList usuarios = null;
            try {
                JSONArray json = new JSONArray(string);
                usuarios = new ArrayList();
                for (int i = 0; i < json.length(); i++) {
                    JSONObject object = json.getJSONObject(i);
                    usuarios.add(new Usuario(object.getString("nif"), object.getString("nombre"), object.getString("apellidos"), object.getString("poblacion"), object.getString("calle"), object.getBoolean("administrador"), object.getString("username"), object.getString("password")));
                }

            } catch (JSONException ex) {
                Logger.getLogger(parseJson.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                return usuarios;
            }
        }

        public static ArrayList montarPedidoProducto(String string) throws JSONException {

            JSONArray json = new JSONArray(string);
            ArrayList pedidoProducto = new ArrayList();
            for (int i = 0; i < json.length(); i++) {
                JSONObject object = json.getJSONObject(i);
                JSONObject object2 = object.getJSONObject("pedido");
                JSONObject object3 = object.getJSONObject("producto");
                PedidoProducto pePro = new PedidoProducto();
                Pedido ped = new Pedido();
                Producto pro = new Producto();
                pro.setNombre(object3.getString("nombre"));
                pePro.setCantidad(object.getInt("cantidad"));
                ped.setId(object2.getLong("id"));
                pePro.setProducto(pro);

                pePro.setPedido(ped);
                pedidoProducto.add(pePro);
            }

            return pedidoProducto;
        }

        public static ArrayList montarHoraBajada(String string) throws JSONException {
            JSONObject json = new JSONObject(string);
            ArrayList data = new ArrayList();
            for (int i = 0; i < json.length(); i++) {
                Horas hora = new Horas();
                hora.setFecha(json.getString("value"));
                data.add(hora);
            }

            return data;
        }
}
