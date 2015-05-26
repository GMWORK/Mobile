package com.proyecto.gmwork.proyectoandroid.view.Cliente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

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
public class seleClientesAVisitar extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private AdapterListClienteSeleccionarVisita adapter;
    private PersistencyController per;
    private TreeMap<String, Boolean> checked;
    private String username;
    private Bundle bun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sele_cliente_a_visitar);
        setResources();
        setResourcesFormat();
        setEvents();
    }

    private void setEvents() {
        btn_aVisitar.setOnClickListener(this);
        btn_filtro.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
    }

    private void setResourcesFormat() {
        lv_clientes.setAdapter(adapter);
    }

    private void setResources() {
        bun = getIntent().getExtras();
        username = bun.getString("username");
        lv_clientes = (ListView) findViewById(R.id.asacav_lv_clientes);
        btn_aVisitar = (Button) findViewById(R.id.ascav_btn_aVisitar);
        btn_filtro = (Button) findViewById(R.id.ascav_btn_filtro);
        spn_campo = (Spinner) findViewById(R.id.ascav_spn_campo);
        sv_search = (SearchView) findViewById(R.id.asacav_sv_search);
        per = new PersistencyController(this);
        btn_finish = (Button) findViewById(R.id.ascav_btn_finish);
        checked = new TreeMap<String, Boolean>();
        adapter = new AdapterListClienteSeleccionarVisita(this, (ArrayList<Cliente>) per.mostrarClientes(username));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ascav_btn_aVisitar:
                aVisitar();
                break;
            case R.id.ascav_btn_filtro:
                adapter = new AdapterListClienteSeleccionarVisita(this, per.filtroCliente(spn_campo.getSelectedItem().toString(), sv_search.getQuery().toString(), username));
                lv_clientes.setAdapter(adapter);
                break;
        }
        sv_search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapter = new AdapterListClienteSeleccionarVisita(seleClientesAVisitar.this, (ArrayList<Cliente>) per.mostrarClientes(username));
                lv_clientes.setAdapter(adapter);
                return true;
            }
        });
    }

    private void aVisitar() {
        Intent intent = new Intent(this, MapaseleClientesAVisitar.class);
        intent.putExtra("clientes", per.clientesAVisitar(checked));
        startActivity(intent);
    }

    private ListView lv_clientes;
    private Button btn_aVisitar;
    private Button btn_filtro;
    private Spinner spn_campo;
    private SearchView sv_search;
    private Button btn_finish;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final CheckBox chec = (CheckBox) view.findViewById(R.id.lacv_check_avisitar);
        final String nif = adapter.getItem(position).getNif();
        chec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chec.isChecked()) {
                    checked.put(nif, true);
                } else {
                    checked.remove(nif);
                }
            }
        });
    }
}
