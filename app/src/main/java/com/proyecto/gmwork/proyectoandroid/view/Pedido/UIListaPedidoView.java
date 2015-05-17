package com.proyecto.gmwork.proyectoandroid.view.Pedido;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.proyecto.gmwork.proyectoandroid.R;

/**
 * Created by mateo on 30/04/15.
 */
public class UIListaPedidoView extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_pedidos);
        setResources();
        setEvents();
    }


    private void setResources() {
        btn_Crear = (Button) findViewById(R.id.agp_btn_Crear);


    }

    private void setEvents() {
        btn_Crear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.agp_btn_Crear:
                crearRegistro();
                break;

        }
    }

    private void crearRegistro() {
        Intent intent = new Intent(this, UICrearPedidoView.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private ListView lv_lista;
    private Button btn_Crear;
}
