package com.proyecto.gmwork.proyectoandroid.view.Cliente;

import android.app.Activity;
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

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Matthew on 21/05/2015.
 */
public class VerClientesCercanos extends Activity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, AdapterView.OnItemSelectedListener, View.OnClickListener {
    private GoogleMap map;
    private LatLngBounds bounds;
    private Spinner cmbTipusMapa;
    ArrayList<HashMap<String, String>> menuItems;
    final String KEY_ITEM = "punto"; // parent node
    final String KEY_ID = "nombre";
    final String KEY_NAME = "latitud";
    final String KEY_COST = "longitud";
    private Button btnCentrar;
    static final String ATOM_NAMESPACE = "http://www.w3.org/2005/Atom";
    private RootElement root = new RootElement(ATOM_NAMESPACE, "feed");
    private ToggleButton btnAnimacio;
    private static final LatLng INS_BOSC_DE_LA_COMA = new LatLng(42.1727, 2.47631);
    private static final String INS_BOSC_DE_LA_COMA_STR = "BOSC DE LA XOMA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuItems = new ArrayList<HashMap<String, String>>();
       /* try {
            parseXML();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }*/
        configurarMapa();

        cmbTipusMapa = (Spinner) findViewById(R.id.spinner);
        cmbTipusMapa.setOnItemSelectedListener(this);
        btnCentrar = (Button) findViewById(R.id.btnCentrar);
        btnCentrar.setOnClickListener(this);
        btnAnimacio = (ToggleButton) findViewById(R.id.aniToggle);
        // map = ((MapFragment) getFragmentManager().findFragmentById(R.id.ID)).getMap();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String tipus = (String) parent.getItemAtPosition(position);
        if (tipus.compareTo("Normal") == 0) {
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        } else if (tipus.compareTo("HÃ­brid") == 0) {
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        } else if (tipus.compareTo("TopogrÃ fic") == 0) {
            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        } else if (tipus.compareTo("SatÃ¨lÂ·lit") == 0) {
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onMapLoaded() {
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
    private void configurarMapa() {     // Fer una comprovaciÃ³ de l'objecte map amb null per confirmar
        // que no l'hÃ gim instanciat prÃ¨viament
        if (map == null) {
            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.ID)).getMap();
            map.setMyLocationEnabled(true);
            // Comprovar si s'ha obtingut correctament l'objecte
            for (int i = 0 ; i< menuItems.size();i++){
                HashMap<String,String> hmap = menuItems.get(i);
                String nombre = hmap.get(KEY_ID);
                String latitud =  hmap.get(KEY_NAME);
                String longitud = hmap.get(KEY_COST);
                LatLng lat  = new LatLng(Double.parseDouble(latitud),Double.parseDouble(longitud));
                map.addMarker(new MarkerOptions().position(lat)
                        // la posiciÃ³
                        .title(nombre).snippet(nombre));
            }

            map.addMarker(new MarkerOptions().position(INS_BOSC_DE_LA_COMA)
                    // la posiciÃ³
                    .title(INS_BOSC_DE_LA_COMA_STR).snippet("Estudis: ESO, Batxillerat, Cicles Formatius i CAS"));
            // el tÃ­tol // un fragment de text

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
            case R.id.aniToggle:
                pintar();
                break;
        }

    }
    private void pintar() {
        PolygonOptions rectOptions =
                new PolygonOptions().add(new LatLng(42.1627, 2.46631))
                        // Nord del punt anterior, perÃ² a la mateixa longitud
                        .add(new LatLng(42.1827, 2.46631))
                                // Mateixa latitud, perÃ² a uns kms a l'oest
                        .add(new LatLng(42.1827, 2.48631))
                                // Mateixa longitud, perÃ² uns kms al sud
                        .add(new LatLng(42.1627, 2.48631))
                                // Tancar el polÃ­gon
                        .add(new LatLng(42.1627, 2.46631));
        // Assignar un color
        rectOptions.fillColor(Color.RED);
        // Afegir el nou polÃ­gon
        Polygon poligon = map.addPolygon(rectOptions);
    }

    private void centrar() {
        if (btnAnimacio.isChecked()) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(INS_BOSC_DE_LA_COMA, 15), 2000, null);
        } else {
            // Moure la cÃ mera a les coordendes del punt que ens interessa
            map.moveCamera(
                    CameraUpdateFactory.newLatLng(INS_BOSC_DE_LA_COMA));
        }
    }
}
