package com.proyecto.gmwork.proyectoandroid.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.Model.Producto;
import com.proyecto.gmwork.proyectoandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 17/05/2015.
 */
public class AdapterListProductos extends ArrayAdapter<Producto> {

    private Context ctx = null;
    private List<Producto> listarray = null;
    private Activity mInflater = null;

    public AdapterListProductos(Context context,  ArrayList<Producto> productos) {
        super(context, R.layout.lista_productos_adapter, productos);
        ctx = context;
        listarray = productos;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public void UpdateArray(List<Producto> lista) {
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
            view = inflator.inflate(R.layout.lista_productos_adapter, null);
        } else
            view = convertView;

        if (view != null) {
            TextView tv_nombre = (TextView) view.findViewById(R.id.lpa_tv_nombre);
            TextView tv_precio = (TextView) view.findViewById(R.id.lpa_tv_precio);
            TextView tv_categoria = (TextView) view.findViewById(R.id.lpa_tv_categoria);
            ImageView iv_img = (ImageView) view.findViewById(R.id.lpa_iv_img);


            if (0 <= position && position < listarray.size()) {
                tv_nombre.setText(String.valueOf(listarray.get(position).getNombre()));
                tv_precio.setText(String.valueOf(listarray.get(position).getPrecio()));
                tv_categoria.setText(listarray.get(position).getCategoria().getNombre());

                if (listarray.get(position).getImg() != null) {
                    iv_img.setImageBitmap(BitmapFactory.decodeByteArray(listarray.get(position).getImg(), 0, listarray.get(position).getImg().length));

                }
            } else {
                tv_nombre.setText("");
                tv_precio.setText("");
                tv_categoria.setText("");
            }
        }
        return view;

    }

}
