package com.proyecto.gmwork.proyectoandroid.view.Cliente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.gmwork.proyectoandroid.R;
import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;
import com.proyecto.gmwork.proyectoandroid.view.Calendario;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by mateo on 30/04/15.
 */
public class UIEditarClienteView extends Activity implements  View.OnClickListener, View.OnKeyListener {
    private PersistencyController per;
    private String nif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);
        Bundle bun = getIntent().getExtras();
        nif = bun.getString("nif");

        try {
            setResources();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setEvents();
        try {
            setResourcesFormat();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setEvents() {
        btn_gCli.setOnClickListener(this);
        btn_sF.setOnClickListener(this);

    }

    private void setResourcesFormat() throws SQLException {
        et_nif.setText(per.filtrarCliente(nif).getNif());
        et_nombre.setText(per.filtrarCliente(nif).getNombre());
        et_apellidos.setText(per.filtrarCliente(nif).getApellidos());
        et_poblacion.setText(per.filtrarCliente(nif).getPoblacion());
        et_calle.setText(per.filtrarCliente(nif).getCalle());


        et_proximaVisita.setText(per.filtrarCliente(nif).getProximaVisita());
    }

    private void setResources() throws SQLException {
        et_nif = (EditText) findViewById(R.id.aec_et_nif);
        et_nombre = (EditText) findViewById(R.id.aec_et_nombre);
        et_apellidos = (EditText) findViewById(R.id.aec_et_apellidos);
        et_poblacion = (EditText) findViewById(R.id.aec_et_poblacion);
        et_calle = (EditText) findViewById(R.id.aec_et_calle);
        et_proximaVisita = (TextView) findViewById(R.id.aec_et_Proxima_Visita);
        btn_gCli = (Button) findViewById(R.id.aec_btn_gCli);
        btn_sF = (Button) findViewById(R.id.aec_btn_sF);
        per = new PersistencyController(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aec_btn_sF:
                new Calendario(this,et_proximaVisita);
                break;
            case R.id.aec_btn_gCli:
                try {
                    editarCliente();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void editarCliente() throws SQLException {
        String nif = et_nif.getText().toString();
        String nombre = et_nombre.getText().toString();
        String apellidos = et_apellidos.getText().toString();
        String poblacion = et_poblacion.getText().toString();
        String calle = et_calle.getText().toString();
        String proximaVisita = et_proximaVisita.getText().toString();

        if (nif.length() > 0 && nombre.length() > 0 && apellidos.length() > 0 && poblacion.length() > 0 && calle.length() > 0 && proximaVisita.length() > 0) {
            per.editarCliente(this.nif,et_nif.getText().toString(), et_nombre.getText().toString(), et_apellidos.getText().toString(), et_poblacion.getText().toString(), et_calle.getText().toString(), et_proximaVisita.getText().toString());
            finish();
        } else {
            Toast.makeText(this, "Tienes que rellenar todos los campos", Toast.LENGTH_SHORT).show();

        }

    }


    private EditText et_nif;
    private EditText et_nombre;
    private EditText et_apellidos;
    private EditText et_poblacion;
    private EditText et_calle;
    private TextView et_proximaVisita;
    private Button btn_gCli;
    private Button btn_sF;


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch(v.getId()){
            case R.id.agc_et_search:
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                filtrar();
                    return true;
                }
                break;
        }
        return false;
    }

    private void filtrar() {

    }
}
