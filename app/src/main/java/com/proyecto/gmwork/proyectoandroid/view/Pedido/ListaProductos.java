package com.proyecto.gmwork.proyectoandroid.view.Pedido;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.proyecto.gmwork.proyectoandroid.R;

/**
 * Created by Mateo on 27/05/2015.
 */
public class ListaProductos extends ActionBarActivity implements AdapterView.OnItemClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);
        setResources();
    }

    private void setResources() {
        list = (ListView) findViewById(R.id.alp_lv_lista_productos);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,SeleccionaCantidad.class);
        startActivity(intent);
    }
    private ListView list;
}
