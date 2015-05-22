package com.proyecto.gmwork.proyectoandroid.controller.utilidades;

import com.proyecto.gmwork.proyectoandroid.Model.CategoriaLog;
import com.proyecto.gmwork.proyectoandroid.Model.ProductoLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Matthew on 22/05/2015.
 */
public class parseJsonLogs {
    public ArrayList parseCategoriaLog(String s) throws JSONException {
        ArrayList list = new ArrayList();
        JSONArray array = new JSONArray(s);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            CategoriaLog catLog = new CategoriaLog();
            catLog.setOperacion(object.getString("Op"));
            catLog.setFecha(object.getString("fecha"));
            catLog.setidCategoria(object.getInt("idCategoria"));
            list.add(catLog);
        }
        return list;
    }
    public ArrayList parseProductoLog(String s) throws JSONException {
        ArrayList list = new ArrayList();
        JSONArray array = new JSONArray(s);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            ProductoLog catLog = new ProductoLog();
            catLog.setOperacion(object.getString("Op"));
            catLog.setFecha(object.getString("fecha"));
            catLog.setIdProducto(object.getInt("idCategoria"));
            list.add(catLog);
        }
        return list;
    }
}
