package com.proyecto.gmwork.proyectoandroid.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.proyecto.gmwork.proyectoandroid.R;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setResources();
        setResourcesFormat();
    }

    private void setResourcesFormat() {
        btn_Cliente.setOnClickListener(this);
        btn_Pedido.setOnClickListener(this);

    }

    private void setResources() {
        btn_Cliente = (Button) findViewById(R.id.am_bt_cli);
        btn_Pedido = (Button) findViewById(R.id.am_bt_pedido);


    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.am_bt_cli:

                break;
        }

    }
    private Button btn_Pedido;
    private Button btn_Cliente;

    private GridView grid;


}
