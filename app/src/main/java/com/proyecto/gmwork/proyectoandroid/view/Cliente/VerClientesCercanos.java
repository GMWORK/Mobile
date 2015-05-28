package com.proyecto.gmwork.proyectoandroid.view.Cliente;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.sax.RootElement;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.proyecto.gmwork.proyectoandroid.R;
import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Matthew on 21/05/2015.
 */
public class VerClientesCercanos extends Activity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, AdapterView.OnItemSelectedListener, View.OnClickListener {
    private PersistencyController per;
    private GoogleMap map;
    private Spinner cmbTipusMapa;
    ArrayList<HashMap<String, String>> menuItems;
    private Button btnCentrar;
    private String username;
    private Bundle bun;
    private Button btn_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_clientes_cercanos);
        setResources();
        setResourcesFormat();
        setEvents();
    }

    private void setEvents() {
        btnCentrar.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
    }

    private void setResourcesFormat() {

    }

    private void setResources() {
        bun = getIntent().getExtras();
        username = bun.getString("username");
        menuItems = new ArrayList<HashMap<String, String>>();
        per = new PersistencyController(this);
        cmbTipusMapa = (Spinner) findViewById(R.id.spinner);
        btnCentrar = (Button) findViewById(R.id.btnCentrar);
        btn_finish = (Button) findViewById(R.id.avcc_btn_finish);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String tipus = (String) parent.getItemAtPosition(position);
        if (tipus.compareTo("Normal") == 0) {
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        } else if (tipus.compareTo("Híbrid") == 0) {
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        } else if (tipus.compareTo("Topogràfic") == 0) {
            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        } else if (tipus.compareTo("Satèl·lit") == 0) {
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onMapLoaded() {
        try {
            configurarMapa();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void configurarMapa() throws SQLException {     // Fer una comprovaciÃ³ de l'objecte map amb null per confirmar
        // que no l'hÃ gim instanciat prÃ¨viament
        if (map == null) {
            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.ID)).getMap();
            map.setMyLocationEnabled(true);
            // Comprovar si s'ha obtingut correctament l'objecte
            for (int i = 0; i < per.getCLienteCercanos(username).size(); i++) {
                String nombre = per.getCLienteCercanos(username).get(i).getNombre();
                String latitud = String.valueOf(per.getCLienteCercanos(username).get(i).getLatitud());
                String longitud = String.valueOf(per.getCLienteCercanos(username).get(i).getLongitud());
                LatLng lat = new LatLng(Double.parseDouble(latitud), Double.parseDouble(longitud));
                pintar(lat);
                map.addMarker(new MarkerOptions().position(lat)
                        .title(nombre).snippet(nombre));

            }
            map.moveCamera(
                    CameraUpdateFactory.newLatLng(per.getMiUbicacion()));

            if (map != null) {
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map.setOnMapLoadedCallback(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCentrar:
                centrar();
                break;
            case R.id.avcc_btn_finish:
                finish();
                break;

        }

    }

    private void pintar(LatLng aPintar) {
        PolygonOptions rectOptions =
                new PolygonOptions().add(aPintar);
        // Assignar un color
        rectOptions.fillColor(Color.BLUE);
        // Afegir el nou polÃ­gon
        Polygon poligon = map.addPolygon(rectOptions);
    }

    private void centrar() {

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(per.getMiUbicacion(), 15), 2000, null);
        // Moure la cÃ mera a les coordendes del punt que ens interessa
        map.moveCamera(
                CameraUpdateFactory.newLatLng(per.getMiUbicacion()));

    }
}
