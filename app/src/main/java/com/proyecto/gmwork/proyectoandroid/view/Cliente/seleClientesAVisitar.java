package com.proyecto.gmwork.proyectoandroid.view.Cliente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.R;
import com.proyecto.gmwork.proyectoandroid.controller.AdapterListClienteSeleccionarVisita;
import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by Mateo on 24/05/2015.
 */
public class seleClientesAVisitar extends Activity implements View.OnClickListener  , AdapterView.OnItemClickListener{
    private AdapterListClienteSeleccionarVisita adapter;
    private PersistencyController per;
    private TreeMap<String,Boolean> checked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sele_cliente_a_visitar);
        try {
            setResources();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setResourcesFormat();
        setEvents();
    }

    private void setEvents() {
        btn_aVisitar.setOnClickListener(this);
    }

    private void setResourcesFormat() {
        lv_clientes.setAdapter(adapter);
    }

    private void setResources() throws SQLException {
        lv_clientes = (ListView) findViewById(R.id.asacav_lv_clientes);
        btn_aVisitar = (Button) findViewById(R.id.ascav_btn_aVisitar);
        per = new PersistencyController(this);
        checked = new TreeMap<String,Boolean>();
        adapter = new AdapterListClienteSeleccionarVisita(this, (ArrayList<Cliente>) per.mostrarClientes());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ascav_btn_aVisitar:
                aVisitar();
                break;
        }

    }

    private void aVisitar() {
        Intent  intent = new Intent(this,MapaseleClientesAVisitar.class);
        intent.putExtra("clientes",per.clientesAVisitar(checked));
        startActivity(intent);
    }

    private ListView lv_clientes;
    private Button btn_aVisitar;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final CheckBox chec = (CheckBox) view.findViewById(R.id.lacv_check_avisitar);
        final String nif = adapter.getItem(position).getNif();
        chec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chec.isChecked()){
                    checked.put(nif,true);
                }else{
                    checked.remove(nif);
                }
            }
        });
    }
}
