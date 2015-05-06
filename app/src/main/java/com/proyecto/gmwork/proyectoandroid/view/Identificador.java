package com.proyecto.gmwork.proyectoandroid.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.gmwork.proyectoandroid.R;
import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;

/**
 * Created by Matthew on 05/05/2015.
 */
public class Identificador extends Activity {
    private PersistencyController per;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResources();
        if(per.hacerLogin(username.getText().toString(), password.getText().toString())){
            pasarPantalla();
        }else{
            Toast.makeText(this,R.string.login_error,Toast.LENGTH_SHORT);
        }





    }
    private void setResources(){
        username = (EditText) findViewById(R.id.la_et_Username);
        password = (EditText) findViewById(R.id.la_et_Password);
        per = new PersistencyController();

    }
    public void pasarPantalla(){
        Intent intent = new Intent(this,Menu.class);
        startActivity(intent);
    }
    private EditText username;
    private EditText password;


}
