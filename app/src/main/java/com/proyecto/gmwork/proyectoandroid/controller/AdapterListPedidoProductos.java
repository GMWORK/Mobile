package com.proyecto.gmwork.proyectoandroid.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.proyecto.gmwork.proyectoandroid.Model.Producto;
import com.proyecto.gmwork.proyectoandroid.R;

import java.util.List;

/**
 * Created by mateo on 19/05/15.
 */
public class AdapterListPedidoProductos extends ArrayAdapter<Producto> {
    Context ctx = null;
    List<Producto> listarray = null;
    private Activity mInflater = null;

    public AdapterListPedidoProductos(Context context,  List<Producto> pedidoProductos) {
        super(context, R.layout.lista_pedidoproducto_adapter, pedidoProductos);
        this.mInflater = (Activity) context;
        listarray = pedidoProductos;
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
            view = inflator.inflate(R.layout.lista_pedidoproducto_adapter, null);
        } else
            view = convertView;

        if (view != null) {
            TextView tv_nombre = (TextView) view.findViewById(R.id.lppa_tv_nombre);
            TextView tv_precio = (TextView) view.findViewById(R.id.lppa_tv_precio);
            TextView tv_precioTotal = (TextView) view.findViewById(R.id.lppa_tv_precioTotal);
            TextView tv_cantidad = (TextView) view.findViewById(R.id.lppa_tv_cantidad);
            Button btn_sumar = (Button) view.findViewById(R.id.lppa_btn_sumar);
            Button btn_restar = (Button) view.findViewById(R.id.lppa_btn_restar);
            if (0 <= position && position < listarray.size()) {
                tv_nombre.setText(String.valueOf(listarray.get(position).getNombre()));
                tv_precio.setText(String.valueOf(listarray.get(position).getPrecio()));
                tv_precioTotal.setText(String.valueOf(listarray.get(position).getPrecio() * Integer.valueOf(tv_cantidad.getText().toString())));

              //  tv_cantidad.setText(String.valueOf(listarray.get(position).getCantidad()));

            } else {
                tv_nombre.setText("");
                tv_precio.setText("");
                tv_precioTotal.setText("");
                tv_cantidad.setText("");
            }
        }
        return view;

    }

}
