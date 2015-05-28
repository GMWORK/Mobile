package com.proyecto.gmwork.proyectoandroid.view.Pedido;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.proyecto.gmwork.proyectoandroid.R;

/**
 * Created by Mateo on 27/05/2015.
 */
public class SeleccionaCantidad extends ActionBarActivity implements  View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        setResources();
        setResourcesFormat();
        setEvents();
    }


    private void setResources() {

    }


    private void setResourcesFormat() {

    }

    private void setEvents() {
    }

    private TextView tv_nombre;
    private TextView tv_categoria;
    private TextView tv_precio;
    private TextView tv_precioDescuento;
    private Button btn_aProducto;

    @Override
    public void onClick(View v) {

    }
}
