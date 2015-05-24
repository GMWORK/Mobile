package com.proyecto.gmwork.proyectoandroid.view.Pedido;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
public class UIListaPedidoView extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private AdapterListPedidos adapter;
    private PersistencyController per;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_pedidos);
        try {
            setResources();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setResourcesFormat();
        setEvents();
    }

    private void setResourcesFormat() {

        lv_lista.setAdapter(adapter);
        registerForContextMenu(lv_lista);
    }


    private void setResources() throws SQLException {
        btn_Crear = (Button) findViewById(R.id.agp_btn_Crear);
        lv_lista = (ListView) findViewById(R.id.agp_lv_lista);
        per = new PersistencyController(this);
        adapter = new AdapterListPedidos(this, (ArrayList<Pedido>) per.mostrarPedido());


    }

    private void setEvents() {
        btn_Crear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.agp_btn_Crear:
                crearRegistro();
                break;

        }
    }

    private void crearRegistro() {
        Intent intent = new Intent(this, UICrearPedidoView.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
                try {
                    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    int listPosition = info.position;
                    per.removePedido(adapter.getItem(listPosition).getId());
                    Toast.makeText(this, "Se ha borrado correctamente el registro", Toast.LENGTH_SHORT).show();
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

            case R.id.mcc_it_editar:
                AdapterView.AdapterContextMenuInfo in = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int position = in.position;
                editarPedido( adapter.getItem(position).getId());
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
}
