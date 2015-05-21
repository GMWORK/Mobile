package com.proyecto.gmwork.proyectoandroid.view;

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
import com.proyecto.gmwork.proyectoandroid.controller.AdapterListProductos;
import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Matthew on 21/05/2015.
 */
public class DialogMessage extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Collections.addAll(mOfficeListItems, getResources().getStringArray(R.array.offices));
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog, null);




        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Informacion").setView(v);
        builder.setMessage("Este programa guardar los datos para su uso de forma local, si es la primera vez que usa este programa por favor dele al boton generar datos locales ya que si no podra usar este programa con sus datos")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getDialog().dismiss();
            }
        });


        return builder.create();
    }

}
