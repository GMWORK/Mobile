package com.proyecto.gmwork.proyectoandroid.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.gmwork.proyectoandroid.R;
import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Matthew on 05/05/2015.
 */
public class Identificador extends Activity implements View.OnClickListener{
    private PersistencyController per;
    private String Content;
    private String Error = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //AlertDialog alert = new AlertDialog(this);
        new DialogMessage().show(getFragmentManager(),"tag");
        try {
            setResources();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setResourcesFormat();
        setEvents();
        //prueba.setText(Content);

    }

    private void setEvents() {
        btn_dInfo.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    private void setResources() throws SQLException {
        username = (EditText) findViewById(R.id.la_et_Username);
        password = (EditText) findViewById(R.id.la_et_Password);
        button = (Button) findViewById(R.id.la_btn_login);
        btn_dInfo = (Button) findViewById(R.id.am_btn_dInfo);
        //prueba = (TextView) findViewById(R.id.Prueba);
        per = new PersistencyController(this);


    }

    private void setResourcesFormat(){

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.la_btn_login:
                try {
                    pasarPantalla();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.am_btn_dInfo:
                try {
                    per.guardarDatosBajadosActivar();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    public void pasarPantalla() throws SQLException {
        if(per.hacerLogin(username.getText().toString(), password.getText().toString())){
            Toast.makeText(this,"Usuario encontrado",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,Menu.class);
            intent.putExtra("username",username.getText().toString());
            startActivity(intent);
        }else{
            Toast.makeText(this,R.string.login_error,Toast.LENGTH_SHORT).show();
        }
    }
    private EditText username;
    private EditText password;
    private Button button;
    private Button btn_dInfo;



}
