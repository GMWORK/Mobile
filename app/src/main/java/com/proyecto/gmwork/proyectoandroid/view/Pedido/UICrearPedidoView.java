package com.proyecto.gmwork.proyectoandroid.view.Pedido;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.proyecto.gmwork.proyectoandroid.Model.PedidoProducto;
import com.proyecto.gmwork.proyectoandroid.R;
import com.proyecto.gmwork.proyectoandroid.controller.AdapterListPedidoProductos;
import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;
import com.proyecto.gmwork.proyectoandroid.view.Calendario;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.ArrayList;

public class UICrearPedidoView extends Activity implements View.OnClickListener ,AdapterView.OnItemClickListener {
    private AdapterListPedidoProductos adapter;
    private PersistencyController per;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pedido);
        try {
            setResources();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            setResourcesFormat();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setEvents();

    }

    private void setEvents()  {
        btn_sF.setOnClickListener(this);
        btn_asCliente.setOnClickListener(this);
        btn_aProducto.setOnClickListener(this);
        btn_altaPedido.setOnClickListener(this);
    }

    private void setResourcesFormat() throws SQLException {
        adapter = new AdapterListPedidoProductos(this,R.layout.lista_pedidoproducto_adapter);
       // lv_productos.setAdapter(adapter);
        lv_productos.setOnItemClickListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, per.nombresProductos());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_productos.setAdapter(dataAdapter);
        number.setMaxValue(30);
        number.setMinValue(1);


    }

    private void setResources() throws SQLException {
        btn_sF = (Button) findViewById(R.id.acp_btn_sF);
        btn_asCliente = (Button) findViewById(R.id.acp_btn_asCliente);
        tv_FEntrega = (TextView) findViewById(R.id.acp_et_FEntrega);
        tv_Cliente = (TextView) findViewById(R.id.acp_et_client);
        btn_aProducto = (Button) findViewById(R.id.acp_btn_aProducto);
        lv_productos = (ListView) findViewById(R.id.acp_lv_productos);
        per = new PersistencyController(this);
        spn_productos = (Spinner) findViewById(R.id.acp_sp_productos);
        number = (NumberPicker) findViewById(R.id.acp_number_picker);
        btn_altaPedido = (Button) findViewById(R.id.acp_btn_crPedido);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.acp_btn_sF:
                new Calendario(this,tv_FEntrega);
                break;
            case R.id.acp_btn_asCliente:
                new DialogCliente().show(getFragmentManager(),"tag");
                break;
            case R.id.acp_btn_crPedido:
                try {
                    per.crearPedido(tv_FEntrega.getText().toString(),tv_Cliente.getText().toString(),adapter);
                    this.finishActivity(0);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.acp_btn_aProducto:
                try {
                 adapter=  per.añadirRegistroPedidoProductoAdapter(adapter,spn_productos.getSelectedItem().toString(),number.getValue());
                    lv_productos.setAdapter(adapter);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
    private NumberPicker number;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
