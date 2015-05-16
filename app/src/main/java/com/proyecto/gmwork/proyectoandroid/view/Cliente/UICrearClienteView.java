package com.proyecto.gmwork.proyectoandroid.view.Cliente;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.proyecto.gmwork.proyectoandroid.R;

/**
 * Created by mateo on 30/04/15.
 */
public class UICrearClienteView extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alta_cliente);
        setResources();
        setEvents();
        setResourcesFormat();
        
    }

    private void setEvents() {

    }

    private void setResourcesFormat() {
    }

    private void setResources() {
        et_nif = (EditText) findViewById(R.id.ac_et_nif);
        et_nombre = (EditText) findViewById(R.id.ac_et_nombre);
        et_apellidos = (EditText) findViewById(R.id.ac_et_apellidos);
        et_poblacion = (EditText) findViewById(R.id.ac_et_poblacion);
        et_calle = (EditText) findViewById(R.id.ac_et_calle);
        et_proximaVisita = (EditText) findViewById(R.id.ac_et_Proxima_Visita);

    }
    private EditText et_nif;
    private EditText et_nombre;
    private EditText et_apellidos;
    private EditText et_poblacion;
    private EditText et_calle;
    private EditText et_proximaVisita;
}
