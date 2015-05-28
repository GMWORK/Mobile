package com.proyecto.gmwork.proyectoandroid.view.Cliente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.proyecto.gmwork.proyectoandroid.Model.Cliente;

import com.proyecto.gmwork.proyectoandroid.R;
import com.proyecto.gmwork.proyectoandroid.controller.AdapterListClientes;
import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;
import com.proyecto.gmwork.proyectoandroid.view.Calendario;
import com.proyecto.gmwork.proyectoandroid.view.Pedido.DialogCliente;
import com.proyecto.gmwork.proyectoandroid.view.Pedido.UICrearPedidoView;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by mateo on 30/04/15.
 */
public class UIListaClienteView extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private PersistencyController per;
    private AdapterListClientes adapter;
    private String nombreUsuario;
    private Bundle bun;

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
        btn_finish.setOnClickListener(this);
        btn_Crear.setOnClickListener(this);
    }

    private void setResources() {
        btn_finish = (Button) findViewById(R.id.agc_btn_finish);
        bun = getIntent().getExtras();
        nombreUsuario = bun.getString("username");
        lv_lista = (ListView) findViewById(R.id.agc_lv_lista);
        btn_Crear = (Button) findViewById(R.id.agc_btn_Crear);
        et_search = (SearchView) findViewById(R.id.agc_et_search);


        per = new PersistencyController(this);
        adapter = new AdapterListClientes(this, (ArrayList<Cliente>) per.mostrarClientes(nombreUsuario));


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
        AdapterView.AdapterContextMenuInfo info = null;
        int listPosition = 0;
        switch (item.getItemId()) {
            case R.id.mcc_it_borrar:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                listPosition = info.position;
                per.removeCliente(adapter.getItem(listPosition).getNif());
                Toast.makeText(this, "Se ha borrado correctamente el registro", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mcc_it_aProximaVisita:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                listPosition = info.position;
                Calendario cal = new Calendario(this,per,adapter.getItem(listPosition).getNif());
                Toast.makeText(this, "Actualizado Con Exito", Toast.LENGTH_SHORT).show();

                break;
            case R.id.mcc_it_verUltimaComanda:
                if (adapter.getItem(listPosition).getPedido().size() >= 1) {
                    info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    listPosition = info.position;
                    DialogPedido dialog = new DialogPedido();
                    bun.putString("cliente", adapter.getItem(listPosition).getNif());
                    dialog.setArguments(bun);
                    dialog.show(getFragmentManager(), "tag");
                } else {
                    Toast.makeText(this, "Este Cliente no tiene comandas assignadas", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mcc_it_CrearPedido:
                Intent intent = new Intent(this,UICrearPedidoView.class);
                startActivity(intent);
                break;
            case R.id.mcc_it_editar:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                listPosition = info.position;
                editarCliente(adapter.getItem(listPosition).getNif());
                break;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        adapter = new AdapterListClientes(this, (ArrayList<Cliente>) per.mostrarClientes(nombreUsuario));
        lv_lista.setAdapter(adapter);
    }

    @Override
    public void onContextMenuClosed(Menu menu) {
        adapter = new AdapterListClientes(this, (ArrayList<Cliente>) per.mostrarClientes(nombreUsuario));


        lv_lista.setAdapter(adapter);
        super.onContextMenuClosed(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.agc_btn_finish:
                finish();
                break;
            case R.id.agc_btn_Crear:
                crearRegistro();
                break;
        }

    }

    private void crearRegistro() {
        Intent intent = new Intent(this, UICrearClienteView.class);
        intent.putExtra("username", nombreUsuario);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public void editarCliente(String nif) {
        Intent intent = new Intent(this, UIEditarClienteView.class);
        intent.putExtra("nif", nif);
        startActivity(intent);
    }
    private Button btn_finish;
    private ListView lv_lista;
    private Button btn_Crear;
    private SearchView et_search;
}
