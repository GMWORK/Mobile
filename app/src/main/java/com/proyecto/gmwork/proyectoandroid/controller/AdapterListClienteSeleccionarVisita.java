package com.proyecto.gmwork.proyectoandroid.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.PedidoProducto;
import com.proyecto.gmwork.proyectoandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateo on 24/05/2015.
 */
public class AdapterListClienteSeleccionarVisita extends ArrayAdapter<Cliente> {
    Context ctx = null;
    List<Cliente> listarray = null;
    private Activity mInflater = null;

    public AdapterListClienteSeleccionarVisita(Context context, ArrayList<Cliente> clientes) {
        super(context, R.layout.lista_adapter_clientes_visitar, clientes);
        this.mInflater = (Activity) context;
        ctx = context;
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
            view = inflator.inflate(R.layout.lista_adapter_clientes_visitar, null);
        } else
            view = convertView;
        try{
        if (view != null) {
            TextView tv_nombre = (TextView) view.findViewById(R.id.lacv_tv_nombre);
            TextView tv_apellidos = (TextView) view.findViewById(R.id.lacv_tv_codigo);
            TextView tv_poblacion = (TextView) view.findViewById(R.id.lacv_tv_visitar);
            ImageView iv_img = (ImageView) view.findViewById(R.id.lca_iv_img);
            CheckBox ch_visitar = (CheckBox) view.findViewById(R.id.lacv_check_avisitar);
            if (0 <= position && position < listarray.size()) {
                tv_nombre.setText(String.valueOf(listarray.get(position).getNombre()));
                tv_apellidos.setText(listarray.get(position).getNif());
                tv_poblacion.setText(listarray.get(position).getPoblacion());
                if (listarray.get(position).getImg() != null) {
                    byte[] bitmapdata = android.util.Base64.decode(listarray.get(position).getImg(), Base64.DEFAULT);
                    Bitmap imatge = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                    iv_img.setImageBitmap(imatge);

                }
            } else {
                tv_nombre.setText("");
                tv_apellidos.setText("");
                tv_poblacion.setText("");
            }
        }
        }catch(Exception ex){
            Toast.makeText(ctx, ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return view;

    }
}
