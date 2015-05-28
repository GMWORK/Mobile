package com.proyecto.gmwork.proyectoandroid.view.Cliente;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.proyecto.gmwork.proyectoandroid.R;
import com.proyecto.gmwork.proyectoandroid.controller.AdapterListPedidos;
import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;
import com.proyecto.gmwork.proyectoandroid.view.Pedido.PedidoPlantilla;

/**
 * Created by Mateo on 25/05/2015.
 */
public class DialogPedido extends DialogFragment implements View.OnClickListener {
    private PersistencyController per;
    private Bundle bun;
    private View v;
    private AlertDialog.Builder builder;
    private String cliente;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        setResources();
        setResourcesFormat();
        setEvents();
        return builder.create();
    }


    private void setResources() {
        per = new PersistencyController(getActivity());
        v = getActivity().getLayoutInflater().inflate(R.layout.dialog_plantilla, null);
        lv_list = (ListView) v.findViewById(R.id.dp_lv_uc);
        builder = new AlertDialog.Builder(getActivity());
        bun = getArguments();
        cliente = bun.getString("cliente");
        adapter = new AdapterListPedidos(getActivity(), per.ultimaComanda(cliente));
        builder.setTitle("Crear Comanda con esta de plantilla?").setView(v);
        btn_si = (Button) v.findViewById(R.id.dp_btn_si);
        btn_no = (Button) v.findViewById(R.id.dp_btn_no);


    }

    private void setResourcesFormat() {

        lv_list.setAdapter(adapter);

    }

    private void setEvents() {
        btn_si.setOnClickListener(this);
        btn_no.setOnClickListener(this);
    }

    private ListView lv_list;
    private AdapterListPedidos adapter;


    @Override
    public void onDismiss(DialogInterface dialog) {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dp_btn_si:
                this.dismiss();
                Intent intent = new Intent(getActivity(),PedidoPlantilla.class);
                intent.putExtra("id",adapter.getItem(0).getId());
                startActivity(intent);
                break;
            case R.id.dp_btn_no:
                this.dismiss();
                break;
        }
    }

    private Button btn_si;
    private Button btn_no;
}
