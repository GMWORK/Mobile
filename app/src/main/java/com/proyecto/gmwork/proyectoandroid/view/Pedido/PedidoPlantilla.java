package com.proyecto.gmwork.proyectoandroid.view.Pedido;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.proyecto.gmwork.proyectoandroid.R;
import com.proyecto.gmwork.proyectoandroid.controller.AdapterListPedidoProductos;
import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;
import com.proyecto.gmwork.proyectoandroid.view.Calendario;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by Mateo on 25/05/2015.
 */
public class PedidoPlantilla extends Activity implements View.OnClickListener {
    private AdapterListPedidoProductos adapter;
    private PersistencyController per;
    private Bundle bun;
    private long idPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pedido);
        bun = getIntent().getExtras();
        idPedido = bun.getLong("id");

        setResources();


        setResourcesFormat();


        setEvents();

    }

    private void setEvents() {
  //      btn_sF.setOnClickListener(this);
        btn_asCliente.setOnClickListener(this);
        btn_aProducto.setOnClickListener(this);
        btn_altaPedido.setOnClickListener(this);
    }

    private void setResourcesFormat() {
        tv_Cliente.setText(per.filtrarPedido(idPedido).getCliente().getNif());
//        tv_FEntrega.setText(per.filtrarPedido(idPedido).getFechaEntrega());
        adapter = new AdapterListPedidoProductos(this, (ArrayList) per.getListPedidoProductoFromPedido(per.filtrarPedido(idPedido).getLiniaProducto()));
        lv_productos.setAdapter(adapter);

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

        //adapter = new AdapterListPedidoProductos(this, R.layout.lista_pedidoproducto_adapter);
        // lv_productos.setAdapter(adapter);
      /*  lv_productos.setOnItemClickListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, per.nombresProductos());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_productos.setAdapter(dataAdapter);
        number.setMaxValue(30);
        number.setMinValue(1);*/


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
        btn_altaPedido = (Button) findViewById(R.id.acp_btn_crPedido);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

          /*  case R.id.acp_btn_sF:
                new Calendario(this, tv_FEntrega);
                break;*/
            case R.id.acp_btn_asCliente:
                new DialogCliente().show(getFragmentManager(), "tag");
                break;
            case R.id.acp_btn_crPedido:
                per.crearPedido(DateTime.now().plusMonths(1).toString(),tv_Cliente.getText().toString(), adapter);
                finish();

                break;
            /*case R.id.acp_btn_finish:
                finish();
                break;*/
            case R.id.acp_btn_aProducto:
                adapter = per.añadirRegistroPedidoProductoAdapter(adapter, spn_productos.getSelectedItem().toString(), Integer.parseInt(tv_cantidad.getText().toString()));
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

}
