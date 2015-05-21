package com.proyecto.gmwork.proyectoandroid.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.proyecto.gmwork.proyectoandroid.R;
import com.proyecto.gmwork.proyectoandroid.view.Cliente.UIListaClienteView;
import com.proyecto.gmwork.proyectoandroid.view.Cliente.VerClientesCercanos;
import com.proyecto.gmwork.proyectoandroid.view.Pedido.UIListaPedidoView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by mateo on 04/05/15.
 */
public class Menu extends Activity implements View.OnClickListener {
    private Bundle bun;
    private String nombreUsuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setResources();
        setResourcesFormat();
        setEvents();
    }

    private void setEvents() {
        btn_Cliente.setOnClickListener(this);
        btn_Pedido.setOnClickListener(this);
        btn_seleVisitar.setOnClickListener(this);
        btn_sVisitarGeo.setOnClickListener(this);
    }

   private void setResourcesFormat() {
        tv_Usuario.setText(bun.getString("username"));

    }

    private void setResources() {
        btn_Cliente = (Button) findViewById(R.id.am_bt_cli);
        btn_Pedido = (Button) findViewById(R.id.am_bt_pedido);
        btn_seleVisitar = (Button) findViewById(R.id.am_btn_seleVisitar);
        btn_sVisitarGeo = (Button) findViewById(R.id.am_btn_sVisitarGeo);
        tv_Usuario = (TextView) findViewById(R.id.am_tv_nombreUsuario);
        bun = getIntent().getExtras();



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.am_bt_cli:
                pasarACliente();
                break;
            case R.id.am_bt_pedido:
                pasarAPedido();
                break;
            case R.id.am_btn_seleVisitar:
                pasarASeleccionarVisita();
                break;
            case R.id.am_btn_sVisitarGeo:
                pasarASeleccionarVisitaMasCercana();
                break;
        }

    }

    private void pasarACliente() {
        Intent intent = new Intent(this, UIListaClienteView.class);
        startActivity(intent);

    }

    private void pasarAPedido() {
        Intent intent = new Intent(this, UIListaPedidoView.class);
        startActivity(intent);
    }

    private void pasarASeleccionarVisita() {


    }

    private void pasarASeleccionarVisitaMasCercana() {
        Intent intent = new Intent(this, VerClientesCercanos.class);
        startActivity(intent);

    }

    private Button btn_Pedido;
    private Button btn_Cliente;
    private Button btn_seleVisitar;
    private Button btn_sVisitarGeo;
    private TextView tv_Usuario;

}
