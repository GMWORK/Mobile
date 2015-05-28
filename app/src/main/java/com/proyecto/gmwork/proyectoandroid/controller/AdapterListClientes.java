package com.proyecto.gmwork.proyectoandroid.controller;

/**
 * Created by Matthew on 17/05/2015.
 */

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mateo on 09/03/2015.
 */
public class AdapterListClientes extends ArrayAdapter<Cliente> {

    Context ctx = null;
    List<Cliente> listarray = null;
    private Activity mInflater = null;

    public AdapterListClientes(Context context, ArrayList<Cliente> clientes) {
        super(context, R.layout.lista_clientes_adapter, clientes);
        this.mInflater = (Activity) context;
        listarray = clientes;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public void UpdateArray(List<Cliente> lista) {
        this.listarray = lista;
    }

    /**
     * S'encarga de buscar el xml i assignar valores e eventos
     *
     * @return Devuelve un XML segun las posicion de una array que se le alla pasado
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = mInflater.getLayoutInflater();
            view = inflator.inflate(R.layout.lista_clientes_adapter, null);
        } else
            view = convertView;

        if (view != null) {
            TextView tv_nombre = (TextView) view.findViewById(R.id.lca_tv_nombre);
            TextView tv_apellidos = (TextView) view.findViewById(R.id.lca_tv_apellidos);
            TextView tv_poblacion = (TextView) view.findViewById(R.id.lca_tv_poblacion);
            TextView tv_calle = (TextView) view.findViewById(R.id.lca_tv_calle);
            ImageView iv_img = (ImageView) view.findViewById(R.id.lca_iv_img);

            if (0 <= position && position < listarray.size()) {
                tv_nombre.setText(String.valueOf(listarray.get(position).getNombre()));
                tv_apellidos.setText(listarray.get(position).getApellidos());
                tv_poblacion.setText(listarray.get(position).getPoblacion());
                tv_calle.setText(listarray.get(position).getCalle());
                if (listarray.get(position).getImg() != null) {
                    byte[] bitmapdata = android.util.Base64.decode(listarray.get(position).getImg(), Base64.DEFAULT);
                    Bitmap imatge = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                    iv_img.setImageBitmap(imatge);

                }
            } else {
                tv_nombre.setText("");
                tv_apellidos.setText("");
                tv_poblacion.setText("");
                tv_calle.setText("");
            }
        }
        return view;

    }

}