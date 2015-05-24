package com.proyecto.gmwork.proyectoandroid.view.Cliente;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.proyecto.gmwork.proyectoandroid.R;
import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mateo on 24/05/2015.
 */
public class MapaseleClientesAVisitar extends Activity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, AdapterView.OnItemSelectedListener, View.OnClickListener {
        private Bundle bun;
        private String[] clientes;
        private PersistencyController per;
        private GoogleMap map;
        private Spinner cmbTipusMapa;
        ArrayList<HashMap<String, String>> menuItems;
        private Button btnCentrar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ver_clientes_cercanos);
            try {

                //getFragmentManager().beginTransaction().;

                setResources();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            setResourcesFormat();
            setEvents();

            // map = ((MapFragment) getFragmentManager().findFragmentById(R.id.ID)).getMap();
        }

        private void setEvents() {
            btnCentrar.setOnClickListener(this);

        }

        private void setResourcesFormat() {

        }

        private void setResources() throws SQLException {
            menuItems = new ArrayList<HashMap<String, String>>();
            per = new PersistencyController(this);
            bun = getIntent().getExtras();
            clientes = bun.getStringArray("clientes");

       /* try {
            parseXML();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }*/


            cmbTipusMapa = (Spinner) findViewById(R.id.spinner);

            btnCentrar = (Button) findViewById(R.id.btnCentrar);
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
                cmbTipusMapa.setOnItemSelectedListener(this);
                configurarMapa();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            /*  bounds = new LatLngBounds(new LatLng(20, -130.0), new LatLng(55, -70.0));

        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
        map.addMarker(new MarkerOptions().position(new LatLng(40.801, -96.691)).title("Lincoln, NE"));
        Marker mark = map.addMarker(new MarkerOptions().position(new LatLng(40.801, -96.691)).title("Lincoln, NE"));
        map.addPolyline(new PolylineOptions().add(new LatLng(40.801, -96.691)).add(new LatLng(34.020, -118.412)).add(new LatLng(40.703, -73.980)));    // Lincoln, NE      .add(new LatLng(34.020, -118.412))   // Los Angeles, CA      .add(new LatLng(40.703, -73.980))    // New York, NY  );
        Polyline polly = map.addPolyline(new PolylineOptions().add(new LatLng(40.801, -96.691)).add(new LatLng(34.020, -118.412)).add(new LatLng(40.703, -73.980)));
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (loc == null) {      // fall back to network if GPS is not available      loc = locationManager.getLastKnownLocation(                     LocationManager.NETWORK_PROVIDER);
            loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,   // provider, min time/distance    new LocationListener() {
                new LocationListener() {

                    @Override
                    public void onLocationChanged(Location location) {

                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
        // SW    new LatLng(55,  -70.0))*/

        }



        private void configurarMapa() throws SQLException {     // Fer una comprovaciÃ³ de l'objecte map amb null per confirmar
            // que no l'hÃ gim instanciat prÃ¨viament
            if (map == null) {
                map = ((MapFragment) getFragmentManager().findFragmentById(R.id.ID)).getMap();
                map.setMyLocationEnabled(true);
                // Comprovar si s'ha obtingut correctament l'objecte

                for (int i = 0 ; i< per.getClienteseleccionados(clientes).size();i++){
                    String nombre = per.getClienteseleccionados(clientes).get(i).getNombre();
                    String latitud = String.valueOf(per.getCLienteCercanos().get(i).getLatitud());
                    String longitud =String.valueOf(per.getCLienteCercanos().get(i).getLongitud());
                    LatLng lat  = new LatLng(Double.parseDouble(latitud),Double.parseDouble(longitud));
                    pintar(lat);
                    map.addMarker(new MarkerOptions().position(lat)
                            .title(nombre).snippet(nombre));

                }
                map.moveCamera(
                        CameraUpdateFactory.newLatLng(per.getMiUbicacion()));

                if (map != null) {
                }// El mapa s'ha comprovat. Ara es pot manipular
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

            map.animateCamera(CameraUpdateFactory.newLatLngZoom(per.getMiUbicacion(),50 ), 50, null);
            // Moure la cÃ mera a les coordendes del punt que ens interessa
            map.moveCamera(
                    CameraUpdateFactory.newLatLng(per.getMiUbicacion()));

        }
}
