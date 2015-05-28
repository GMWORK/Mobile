package com.proyecto.gmwork.proyectoandroid.view.Pedido;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.proyecto.gmwork.proyectoandroid.R;
import com.proyecto.gmwork.proyectoandroid.controller.AdapterListProductos;
import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;

import java.util.ArrayList;

/**
 * Created by Matthew on 17/05/2015.
 */
public class DialogProducto extends DialogFragment implements AdapterView.OnItemClickListener {
    private PersistencyController per;
    private View v;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setResources();
        setResourcesFormat();
        setResourcesEvents();
        per = new PersistencyController(getActivity());

        //Collections.addAll(mOfficeListItems, getResources().getStringArray(R.array.offices));
        v = getActivity().getLayoutInflater().inflate(R.layout.dialog_producto, null);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, per.nombresProductos());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        sb_cantidad.setMax(30);

       // adapter = new AdapterListProductos(getActivity(), (ArrayList) per.mostrarProducto());


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Productos").setView(v);

        return builder.create();
    }

    private void setResourcesEvents() {
    }

    private void setResourcesFormat() {

    }

    private void setResources() {
        v = getActivity().getLayoutInflater().inflate(R.layout.dialog_producto, null);
        //spinner = (Spinner) v.findViewById(R.id.dp_sp_productos);
       // sb_cantidad = (SeekBar) v.findViewById(R.id.dp_sb_cantidad);
    }
    private Spinner spinner;
    private TextView tv_Producto;
    private SeekBar sb_cantidad;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
