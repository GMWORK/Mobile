package com.proyecto.gmwork.proyectoandroid.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 17/05/2015.
 */
public class AdapterListPedidos extends ArrayAdapter<Pedido> {

    Context ctx = null;
    List<Pedido> listarray = null;
    private Activity mInflater = null;

    public AdapterListPedidos(Context context, ArrayList<Pedido> pedidos) {
        super(context, R.layout.lista_pedidos_adapter, pedidos);
        this.mInflater = (Activity) context;
        listarray = pedidos;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public void UpdateArray(List<Pedido> lista) {
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
            view = inflator.inflate(R.layout.lista_pedidos_adapter, null);
        } else
            view = convertView;

        if (view != null) {
            TextView tv_pFecha = (TextView) view.findViewById(R.id.lpa_tv_fEntrega);
            TextView tv_id = (TextView) view.findViewById(R.id.lpa_tv_id);
            TextView tv_pTotal = (TextView) view.findViewById(R.id.lpa_tv_pTotal);
            TextView tv_cliente = (TextView) view.findViewById(R.id.lpa_tv_cliente);
            TextView tv_estado = (TextView) view.findViewById(R.id.lpa_tv_estado);

            if (0 <= position && position < listarray.size()) {
                tv_pFecha.setText(String.valueOf(listarray.get(position).getFechaEntrega()));
                tv_id.setText(String.valueOf(listarray.get(position).getId()));
                tv_pTotal.setText(String.valueOf(listarray.get(position).getTotal()));
                tv_cliente.setText(listarray.get(position).getCliente().getNif());
                tv_estado.setText(listarray.get(position).getEstado());

            } else {
                tv_pFecha.setText("");
                tv_id.setText("");
                tv_pTotal.setText("");
                tv_cliente.setText("");
                tv_estado.setText("");
            }
        }
        return view;
    }
}

