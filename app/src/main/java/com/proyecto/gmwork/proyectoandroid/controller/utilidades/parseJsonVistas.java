package com.proyecto.gmwork.proyectoandroid.controller.utilidades;

import com.proyecto.gmwork.proyectoandroid.Model.CategoriaLog;
import com.proyecto.gmwork.proyectoandroid.Model.ProductoLog;
import com.proyecto.gmwork.proyectoandroid.Model.mapping.CategoriaVista;
import com.proyecto.gmwork.proyectoandroid.Model.mapping.ClienteVista;
import com.proyecto.gmwork.proyectoandroid.Model.mapping.PedidoProductoVista;
import com.proyecto.gmwork.proyectoandroid.Model.mapping.PedidoVista;
import com.proyecto.gmwork.proyectoandroid.Model.mapping.ProductoVista;
import com.proyecto.gmwork.proyectoandroid.Model.mapping.UsuarioVista;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Matthew on 22/05/2015.
 */
public class parseJsonVistas {

    public static ArrayList parseCategoriaLog(String s) {
        ArrayList list = new ArrayList();
        JSONArray array = null;

        try {
            array = new JSONArray(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int i = 0;
        while (i < array.length()) {
            try {
                CategoriaVista catv = new CategoriaVista();
                JSONObject object = array.getJSONObject(i);

                if (object.getString("op").equals("D")) {
                    catv.setOp(object.getString("op"));
                    catv.setFecha(DateTime.parse(object.getString("fecha")));
                    catv.setId(object.getInt("idCategoria"));

                } else {
                    catv.setNombre(object.getString("nombre"));
                    catv.setOp(object.getString("op"));
                    catv.setDescuento(object.getDouble("descuento"));
                    catv.setFecha(DateTime.parse(object.getString("fecha")));
                }
                list.add(catv);
                i++;
            } catch (JSONException e) {
                e.printStackTrace();
                i++;
            }

        }
        return list;

    }

    public static ArrayList parsePedidoproductoLog(String s) {
        ArrayList list = new ArrayList();


        JSONArray array = null;
        try {
            array = new JSONArray(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int i = 0;
        while (i < array.length()) {
            try {

                PedidoProductoVista catv = new PedidoProductoVista();
                JSONObject object = array.getJSONObject(i);
                catv.setPedidoid(object.getInt("idPedido"));
                catv.setOp(object.getString("op"));
                catv.setProductoid(object.getInt("idProducto"));
                catv.setFecha(DateTime.parse(object.getString("fecha")));
                list.add(catv);
                i++;


            } catch (JSONException ex) {
                Logger.getLogger(parseJsonVistas.class.getName()).log(Level.SEVERE, null, ex);
                i++;
            }
        }
        return list;
    }


    public static ArrayList parseUsuarioLog(String s) {
        ArrayList list = new ArrayList();


        UsuarioVista catv = null;
        JSONArray array = null;
        try {
            array = new JSONArray(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int i = 0;
        while (i < array.length()) {
            try {
                catv = new UsuarioVista();
                JSONObject object = array.getJSONObject(i);
                if (object.getString("op").equals("D")) {
                    catv.setOp(object.getString("op"));
                    catv.setFecha(DateTime.parse(object.getString("fecha")));
                    catv.setId(object.getInt("idUsuario"));

                } else {
                    catv.setNombre(object.getString("nombre"));
                    catv.setOp(object.getString("op"));
                    catv.setPassword(object.getString("password"));
                    catv.setApellidos(object.getString("apellidos"));
                    catv.setAdministrador(object.getBoolean("administrador"));
                    catv.setNif(object.getString("nif"));
                    catv.setCalle(object.getString("calle"));
                    catv.setFecha(DateTime.parse(object.getString("fecha")));
                }
                list.add(catv);
                i++;
            } catch (JSONException ex) {
                Logger.getLogger(parseJsonVistas.class.getName()).log(Level.SEVERE, null, ex);
                i++;
            }
        }
        return list;
    }

    public static ArrayList parseClienteLog(String s) {
        ArrayList list = new ArrayList();


        JSONArray array = null;
        ClienteVista catv = null;
        try {
            array = new JSONArray(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int i = 0;
        while (i < array.length()) {
            try {
                catv = new ClienteVista();
                JSONObject object = array.getJSONObject(i);
                if (object.getString("op").equals("D")) {
                    catv.setOp(object.getString("op"));
                    catv.setFecha(DateTime.parse(object.getString("fecha")));
                    catv.setId(object.getInt("idCliente"));

                } else {
                    catv.setNombre(object.getString("nombre"));
                    catv.setOp(object.getString("op"));
                    catv.setLatitud(object.getDouble("latitud"));
                    catv.setApellidos(object.getString("apellidos"));
                    if (object.has("img")) {
                        catv.setImg(object.getString("img"));
                    }
                    catv.setUsuarioid(object.getInt("usuarioid"));
                    catv.setNif(object.getString("nif"));
                    catv.setCalle(object.getString("calle"));
                    catv.setFecha(DateTime.parse(object.getString("fecha")));
                }
                list.add(catv);
                i++;

            } catch (JSONException e) {
                e.printStackTrace();
                i++;
            }
        }

        return list;

    }


    public static ArrayList parseProductoVista(String s) {
        ArrayList list = new ArrayList();


        JSONArray array = null;
        ClienteVista catv = null;
        try {
            array = new JSONArray(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int i = 0;
        while (i < array.length()) {
            try {
                JSONObject object = array.getJSONObject(i);
                ProductoVista prov = new ProductoVista();
                if (object.getString("op").equals("D")) {
                    prov.setOp(object.getString("op"));
                    prov.setFecha(DateTime.parse(object.getString("fecha")));
                    prov.setId(object.getInt("idProducto"));

                } else {

                    prov.setId(object.getInt("id"));
                    prov.setNombre(object.getString("nombre"));
                    prov.setCategoriaid(object.getInt("categoriaid"));
                    prov.setOp(object.getString("op"));
                    prov.setInhabilitats(object.getBoolean("inhabilitats"));
                    prov.setPrecio(object.getDouble("precio"));
                    prov.setFecha(DateTime.parse(object.getString("fecha")));
                }
                list.add(prov);
                i++;
            } catch (JSONException e) {
                e.printStackTrace();
                i++;
            }
        }
        return list;

    }

    public static ArrayList parsePedido(String s) {
        ArrayList list = new ArrayList();


        JSONArray array = null;
        ClienteVista catv = null;
        try {
            array = new JSONArray(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int i = 0;
        while (i < array.length()) {
            try {
                JSONObject object = array.getJSONObject(i);
                PedidoVista prov = new PedidoVista();
                if (object.getString("op").equals("D")) {
                    prov.setOp(object.getString("op"));
                    prov.setFecha(DateTime.parse(object.getString("fecha")));
                    prov.setId(object.getInt("idPedido"));
                } else {
                    prov.setId(object.getInt("id"));
                    prov.setEstado(object.getString("estado"));
                    prov.setFechaEntrega(object.getString("fecha"));
                    prov.setOp(object.getString("op"));
                    prov.setIdPedido(object.getInt("idPedido"));
                    // prov.setClienteid(object.getDouble("precio");
                    prov.setFecha(DateTime.parse(object.getString("fecha")));
                }
                list.add(prov);
                i++;
            } catch (JSONException ex) {
                Logger.getLogger(parseJsonVistas.class.getName()).log(Level.SEVERE, null, ex);
                i++;
            }
        }
        return list;
    }

}
