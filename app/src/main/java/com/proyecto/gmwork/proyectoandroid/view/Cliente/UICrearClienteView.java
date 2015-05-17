package com.proyecto.gmwork.proyectoandroid.view.Cliente;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.gmwork.proyectoandroid.R;
import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;
import com.proyecto.gmwork.proyectoandroid.view.Calendario;

import java.sql.SQLException;

/**
 * Created by mateo on 30/04/15.
 */
public class UICrearClienteView extends Activity implements View.OnClickListener {
    private PersistencyController per;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_cliente);
        try {
            setResources();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setEvents();
        setResourcesFormat();

    }

    private void setEvents() {
       /*ViewGroup v = (ViewGroup)this.getWindow().getDecorView().getRootView();
        for(int i = 0 ; i<v.getChildCount() ; i++){
            if(v.getChildAt(i) instanceof Button){
                Button a = (Button) v.getChildAt(i);
                a.setOnClickListener(this);
            }
        }*/
        btn_gCli.setOnClickListener(this);
        btn_sF.setOnClickListener(this);

    }

    private void setResourcesFormat() {
    }

    private void setResources() throws SQLException {
        et_nif = (EditText) findViewById(R.id.aac_et_nif);
        et_nombre = (EditText) findViewById(R.id.aac_et_nombre);
        et_apellidos = (EditText) findViewById(R.id.aac_et_apellidos);
        et_poblacion = (EditText) findViewById(R.id.aac_et_poblacion);
        et_calle = (EditText) findViewById(R.id.aac_et_calle);
        et_proximaVisita = (TextView) findViewById(R.id.aac_et_Proxima_Visita);
        btn_gCli = (Button) findViewById(R.id.aac_btn_gCli);
        btn_sF = (Button) findViewById(R.id.aac_btn_sF);
        per = new PersistencyController(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aac_btn_sF:
                new Calendario(this,et_proximaVisita);
                break;
            case R.id.aac_btn_gCli:
                crearCliente();
                break;
        }
    }

    private void crearCliente() {
        String nif = et_nif.getText().toString();
        String nombre = et_nombre.getText().toString();
        String apellidos = et_apellidos.getText().toString();
        String poblacion = et_poblacion.getText().toString();
        String calle = et_calle.getText().toString();
        String proximaVisita = et_proximaVisita.getText().toString();

        if (nif.length() > 0 && nombre.length() > 0 && apellidos.length() > 0 && poblacion.length() > 0 && calle.length() > 0 && proximaVisita.length() > 0) {
            per.crearCliente(et_nif.getText().toString(), et_nombre.getText().toString(), et_apellidos.getText().toString(), et_poblacion.getText().toString(), et_calle.getText().toString(), et_proximaVisita.getText().toString());
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


}
