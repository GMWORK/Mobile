package com.proyecto.gmwork.proyectoandroid.view.Cliente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.R;
import com.proyecto.gmwork.proyectoandroid.controller.AdapterListClientes;
import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by mateo on 30/04/15.
 */
public class UIListaClienteView extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private PersistencyController per;
    private AdapterListClientes adapter;
    private ArrayList aVisitar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_clientes);
        setResources();
        setEvents();
        setResourcesFormat();
    }

    private void setResourcesFormat() {
        lv_lista.setAdapter(adapter);
        registerForContextMenu(lv_lista);
    }

    private void setEvents() {
        btn_Crear.setOnClickListener(this);
    }

    private void setResources() {

        lv_lista = (ListView) findViewById(R.id.agc_lv_lista);
        btn_Crear = (Button) findViewById(R.id.agc_btn_Crear);
        et_search = (SearchView) findViewById(R.id.agc_et_search);

        aVisitar = new ArrayList();
        try {
            per = new PersistencyController(this);
            adapter = new AdapterListClientes(this, (ArrayList<Cliente>) per.mostrarClientes());
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()) {
            case R.id.agc_lv_lista:
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu_context_clientes, menu);
                super.onCreateContextMenu(menu, v, menuInfo);
                break;
        }


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mcc_it_borrar:
                try {
                    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    int listPosition = info.position;
                    per.removeCliente(adapter.getItem(listPosition).getNif());
                    Toast.makeText(this,"Se ha borrado correctamente el registro",Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            /*case R.id.mcc_it_aVisita:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int listPosition = info.position;
                aVisitar.add(adapter.getItem(listPosition).getNif());
                Toast.makeText(this,"Se ha guardado en la lista de personas a visitar",Toast.LENGTH_SHORT).show();
                break;*/
            case R.id.mcc_it_verUltimaComanda:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int listPosition = info.position;
               per.ultimaComanda(adapter.getItem(listPosition));
                break;
            case R.id.mcc_it_editar:
                AdapterView.AdapterContextMenuInfo in = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int position = in.position;
                editarCliente(adapter.getItem(position).getNif());
                break;
        }
        return super.onContextItemSelected(item);
    }



    @Override
    protected void onResume() {
        super.onResume();
        try {
            adapter = new AdapterListClientes(this, (ArrayList<Cliente>) per.mostrarClientes());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        lv_lista.setAdapter(adapter);
        Toast.makeText(this,"Se han actualizado los datos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onContextMenuClosed(Menu menu) {
        try {
            adapter = new AdapterListClientes(this, (ArrayList<Cliente>) per.mostrarClientes());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        lv_lista.setAdapter(adapter);
        super.onContextMenuClosed(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.agc_btn_Crear:
                crearRegistro();
                break;
        }

    }

    private void crearRegistro() {
        Intent intent = new Intent(this, UICrearClienteView.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    public void editarCliente(String nif){
        Intent intent = new Intent(this,UIEditarClienteView.class);
        intent.putExtra("nif",nif);
        startActivity(intent);
    }

    private ListView lv_lista;
    private Button btn_Crear;
    private SearchView et_search;
}
