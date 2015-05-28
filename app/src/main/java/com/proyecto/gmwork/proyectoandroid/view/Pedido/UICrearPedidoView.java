package com.proyecto.gmwork.proyectoandroid.view.Pedido;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.gmwork.proyectoandroid.R;
import com.proyecto.gmwork.proyectoandroid.controller.AdapterListPedidoProductos;
import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;
import com.proyecto.gmwork.proyectoandroid.view.Calendario;
import com.proyecto.gmwork.proyectoandroid.view.Cliente.DialogPedido;

import org.joda.time.DateTime;

public class UICrearPedidoView extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private AdapterListPedidoProductos adapter;
    private PersistencyController per;
    private Bundle bun;
    private String username;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pedido);
        setResources();
        setResourcesFormat();
        setEvents();

    }

    private void setEvents() {

        //     btn_sF.setOnClickListener(this);
        btn_asCliente.setOnClickListener(this);
        btn_aProducto.setOnClickListener(this);
        btn_altaPedido.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
    }

    private void setResourcesFormat() {

        adapter = new AdapterListPedidoProductos(this, R.layout.lista_pedidoproducto_adapter);
        lv_productos.setAdapter(adapter);
        lv_productos.setOnItemClickListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, per.nombresProductos());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_productos.setAdapter(dataAdapter);
        number.setMax(30);
        number.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_cantidad.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private void setResources() {
        // btn_sF = (Button) findViewById(R.id.acp_btn_sF);
        btn_asCliente = (Button) findViewById(R.id.acp_btn_asCliente);
        //   tv_FEntrega = (TextView) findViewById(R.id.acp_et_FEntrega);
        tv_Cliente = (TextView) findViewById(R.id.acp_et_client);
        btn_aProducto = (Button) findViewById(R.id.acp_btn_aProducto);
        lv_productos = (ListView) findViewById(R.id.acp_lv_productos);
        btn_finish = (Button) findViewById(R.id.acp_btn_finish);
        per = new PersistencyController(this);
        tv_cantidad = (TextView) findViewById(R.id.acp_tv_cantidad);
        spn_productos = (Spinner) findViewById(R.id.acp_sp_productos);
        number = (SeekBar) findViewById(R.id.acp_sb_cantidad);
        registerForContextMenu(lv_productos);
        btn_altaPedido = (Button) findViewById(R.id.acp_btn_crPedido);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()) {
            case R.id.acp_lv_productos:
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu_context_pedidosproductos, menu);
                super.onCreateContextMenu(menu, v, menuInfo);
                break;
        }


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = null;
        int listPosition = 0;
        switch (item.getItemId()) {
            case R.id.mcpp_it_QuitarProducto:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                listPosition = info.position;
                adapter.remove(adapter.getItem(listPosition));
                break;
        }
        return super.onContextItemSelected(item);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.acp_btn_asCliente:
                new DialogCliente().show(getFragmentManager(), "tag");
                break;
            case R.id.acp_btn_crPedido:
                //if (!tv_Cliente.equals("") && !tv_FEntrega.equals("")) {
                per.crearPedido(DateTime.now().plusDays(30).toString(), tv_Cliente.getText().toString(), adapter);
                Calendario cal = new Calendario(this, per, tv_Cliente.getText().toString());
                Intent intent = new Intent(this, UIListaPedidoView.class);
                startActivity(intent);
                break;
            case R.id.acp_btn_finish:
                finish();
                break;
            case R.id.acp_btn_aProducto:
                // new DialogCliente().show(getFragmentManager(), "tag");
                adapter = per.añadirRegistroPedidoProductoAdapter(adapter, spn_productos.getSelectedItem().toString(), Integer.valueOf(tv_cantidad.getText().toString()));
                lv_productos.setAdapter(adapter);

                break;

        }
    }

private Button btn_sF;
private Button btn_asCliente;
private ListView lv_productos;
private TextView tv_FEntrega;
private TextView tv_Cliente;
private Button btn_aProducto;
private Spinner spn_productos;
private Button btn_altaPedido;
private Button btn_finish;
private SeekBar number;
private TextView tv_cantidad;



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
