package com.proyecto.gmwork.proyectoandroid.view.Pedido;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.proyecto.gmwork.proyectoandroid.R;
import com.proyecto.gmwork.proyectoandroid.controller.AdapterListClientes;
import com.proyecto.gmwork.proyectoandroid.controller.AdapterListProductos;
import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Matthew on 17/05/2015.
 */
public class DialogCliente extends DialogFragment implements AdapterView.OnItemClickListener {
    private PersistencyController per;
    private String eleccion;
    private Bundle bun ;
    private String usuario;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
            per = new PersistencyController(getActivity());

        //Collections.addAll(mOfficeListItems, getResources().getStringArray(R.array.offices));
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog, null);

        lv_list = (ListView) v.findViewById(R.id.d_lv_list);





        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        bun =getActivity().getIntent().getExtras();
        usuario = bun.getString("username");
        adapter = new AdapterListClientes(getActivity(), (ArrayList) per.mostrarClientes(usuario));
        lv_list.setAdapter(adapter);
        lv_list.setOnItemClickListener(this);

        builder.setTitle("Clientes").setView(v);

        return builder.create();
    }

    private ListView lv_list;
    private AdapterListClientes adapter;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            TextView a = (TextView) getActivity().findViewById(R.id.acp_et_client);
            a.setText(adapter.getItem(position).getNif());

            this.dismiss();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {


    }
}

