package com.proyecto.gmwork.proyectoandroid.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.proyecto.gmwork.proyectoandroid.Model.PedidoProducto;
import com.proyecto.gmwork.proyectoandroid.Model.Producto;
import com.proyecto.gmwork.proyectoandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateo on 19/05/15.
 */
public class AdapterListPedidoProductos extends ArrayAdapter<PedidoProducto> {
    Context ctx = null;
    List<PedidoProducto> listarray  = null;
    private Activity mInflater = null;

    public AdapterListPedidoProductos(Context context,  ArrayList pedidoProductos) {
        super(context, R.layout.lista_pedidoproducto_adapter, pedidoProductos);
        this.mInflater = (Activity) context;
        listarray = pedidoProductos;
    }

    public AdapterListPedidoProductos(Context context, int resource) {
        super(context, R.layout.lista_pedidoproducto_adapter);


        this.mInflater = (Activity) context;
        listarray = new ArrayList<PedidoProducto>();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public void UpdateArray(PedidoProducto ped) {
        this.listarray.add(ped);
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

            if (0 <= position && position < listarray.size()) {

                    tv_nombre.setText(String.valueOf(listarray.get(position).getProducto().getNombre()));
                    tv_precio.setText(String.valueOf(listarray.get(position).getProducto().getPrecio()));
                    tv_precioTotal.setText(String.valueOf(listarray.get(position).getProducto().getPrecio() * listarray.get(position).getCantidad()));
                    tv_cantidad.setText(String.valueOf(listarray.get(position).getCantidad()));

            } else {
                tv_nombre.setText("");
                tv_precio.setText("");
                tv_precioTotal.setText("");
                tv_cantidad.setText("");
            }
        }
        return view;

    }

    public List<PedidoProducto> getListarray() {
        return listarray;
    }


}
