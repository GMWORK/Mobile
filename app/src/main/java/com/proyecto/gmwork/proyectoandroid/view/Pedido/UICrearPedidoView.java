package com.proyecto.gmwork.proyectoandroid.view.Pedido;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.proyecto.gmwork.proyectoandroid.R;
import com.proyecto.gmwork.proyectoandroid.view.Calendario;

import org.w3c.dom.Text;

public class UICrearPedidoView extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pedido);
        setResources();
        setResourcesFormat();
        setEvents();
    }

    private void setEvents() {

        btn_sF.setOnClickListener(this);
        btn_asCliente.setOnClickListener(this);
        btn_aProducto.setOnClickListener(this);

    }

    private void setResourcesFormat() {

    }

    private void setResources() {
        btn_sF = (Button) findViewById(R.id.acp_btn_sF);
        btn_asCliente = (Button) findViewById(R.id.acp_btn_asCliente);
        tv_FEntrega = (TextView) findViewById(R.id.acp_et_FEntrega);
        tv_Cliente = (TextView) findViewById(R.id.acp_et_client);
        btn_aProducto = (Button) findViewById(R.id.acp_btn_aProducto);
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
            case R.id.acp_btn_aProducto:
                new DialogProducto().show(getFragmentManager(),"tag");
                break;
        }
    }

    private Button btn_sF;
    private Button btn_asCliente;
    private TextView tv_FEntrega;
    private TextView tv_Cliente;
    private Button btn_aProducto;
}