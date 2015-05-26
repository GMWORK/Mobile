package com.proyecto.gmwork.proyectoandroid.view.Pedido;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.proyecto.gmwork.proyectoandroid.R;
import com.proyecto.gmwork.proyectoandroid.controller.AdapterListProductos;
import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Matthew on 17/05/2015.
 */
public class DialogProducto extends DialogFragment  implements AdapterView.OnItemClickListener{
    private PersistencyController per;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

            per = new PersistencyController(getActivity());

        //Collections.addAll(mOfficeListItems, getResources().getStringArray(R.array.offices));
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog, null);

        lv_list = (ListView) v.findViewById(R.id.d_lv_list);

            adapter = new AdapterListProductos(getActivity(), (ArrayList) per.mostrarProducto());
            lv_list.setAdapter(adapter);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Productos").setView(v);

        return builder.create();
    }
    private ListView lv_list;
    private  AdapterListProductos adapter;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView producto = (TextView) view.findViewById(R.id.lppa_tv_nombre);
        getActivity().getIntent().putExtra("Producto"+position+"",producto.getText().toString());
        this.dismiss();
    }
}
