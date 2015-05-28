package com.proyecto.gmwork.proyectoandroid.view.Pedido;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.Model.PedidoProducto;
import com.proyecto.gmwork.proyectoandroid.R;
import com.proyecto.gmwork.proyectoandroid.controller.AdapterListPedidos;
import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;
import com.proyecto.gmwork.proyectoandroid.view.Cliente.UIEditarClienteView;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by mateo on 30/04/15.
 */
public class UIListaPedidoView extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private AdapterListPedidos adapter;
    private PersistencyController per;
    private String usuario;
    private Bundle bun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_pedidos);
        setResources();
        setResourcesFormat();
        setEvents();
    }




    private void setResources() {
        bun = getIntent().getExtras();
        usuario = bun.getString("username");
        btn_Crear = (Button) findViewById(R.id.agp_btn_Crear);
        lv_lista = (ListView) findViewById(R.id.agp_lv_lista);
        btn_finish = (Button) findViewById(R.id.agp_btn_finish);
        per = new PersistencyController(this);
        adapter = new AdapterListPedidos(this, (ArrayList<Pedido>) per.mostrarPedido(usuario));
    }
    private void setResourcesFormat() {

        lv_lista.setAdapter(adapter);
        registerForContextMenu(lv_lista);
    }
    private void setEvents() {
        btn_Crear.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.agp_btn_Crear:
                crearRegistro();
                break;
            case R.id.agp_btn_finish:
                finish();
                break;
        }
    }

    private void crearRegistro() {
        Intent intent = new Intent(this, UICrearPedidoView.class);
        intent.putExtra("username", usuario);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new AdapterListPedidos(this, (ArrayList<Pedido>) per.mostrarPedido(usuario));
        lv_lista.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()) {
            case R.id.agp_lv_lista:
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu_context_pedidos, menu);
                super.onCreateContextMenu(menu, v, menuInfo);
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mcc_it_borrar:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int listPosition = info.position;
                per.removePedido(adapter.getItem(listPosition).getId());
                Toast.makeText(this, "Se ha borrado correctamente el registro", Toast.LENGTH_SHORT).show();
                adapter = new AdapterListPedidos(this, (ArrayList<Pedido>) per.mostrarPedido(usuario));
                lv_lista.setAdapter(adapter);
                break;

            case R.id.mcc_it_editar:
                AdapterView.AdapterContextMenuInfo in = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int position = in.position;
                if (adapter.getItem(position).getEstado().equals("completada")) {
                    Toast.makeText(this, "No se puede editar esta en estado completada", Toast.LENGTH_SHORT).show();
                } else {
                    editarPedido(adapter.getItem(position).getId());
                    adapter = new AdapterListPedidos(this, (ArrayList<Pedido>) per.mostrarPedido(usuario));
                    lv_lista.setAdapter(adapter);
                }
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void editarPedido(long id) {

        Intent intent = new Intent(this, UIEditarPedidoView.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private ListView lv_lista;
    private Button btn_Crear;
    private Button btn_finish;
}
