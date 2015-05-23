package com.proyecto.gmwork.proyectoandroid.controller.utilidades;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Mateo on 22/05/2015.
 */
public class OperacionesTodoDinamico extends AsyncTask<String, Void, Object> {

    private ProgressDialog dialog;
    private Activity activity;

    public OperacionesTodoDinamico(Activity activity) {
        this.dialog = new ProgressDialog(activity);
        this.activity = activity;
    }




    @Override
    protected Object doInBackground(String... params) {

        Object objectToReturn = null;
        switch ((String) params[1]) {
            case "GET":
                objectToReturn = doGetRequest((String) params[0]);
                break;
            case "POST":
                objectToReturn = doPostRequest((String) params[0], (String) params[2]);
                break;
            case "PUT":
                objectToReturn = doPutRequest((String) params[0], (String) params[2]);
                break;
            case "DELETE":
                objectToReturn = doDeleteRequest((String) params[0]);
                break;
            default:
                Log.e("ERROR in doInBackground", "ERROR!");
        }
        return objectToReturn;
    }

    public <T> List<T> getListOfObjectsFromServer(Class<T> objectClass) {
        String serverResponse = getServerResponse(objectClass.getSimpleName(), "GET", null);
        List<T> listOfObjects = ConvertorJsonDinamico.getObjectsFromFormattedJson(objectClass, ConvertorJsonDinamico.formatJsonInput(serverResponse), this.activity);
        return listOfObjects;
    }

    private Object doDeleteRequest(String stringUrl) {
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
        HttpResponse response;
        String stringResponse = null;
        try {
            HttpDelete delete = new HttpDelete(new URI(stringUrl));
            //StringEntity se = new StringEntity(postMessage);
            //se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            //put.setEntity(se);
            response = client.execute(delete);
            if (response != null) {
                InputStream in = response.getEntity().getContent();
                stringResponse = getStringFromInputStream(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringResponse;
    }


    private Object doPutRequest(String stringUrl, String postMessage) {
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
        HttpResponse response;
        String stringResponse = null;
        try {
            HttpPut put = new HttpPut(new URI(stringUrl));
            StringEntity se = new StringEntity(postMessage);
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            put.setEntity(se);
            response = client.execute(put);
            if (response != null) {
                InputStream in = response.getEntity().getContent();
                stringResponse = getStringFromInputStream(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringResponse;
    }
    public String getServerResponse(String resourceUrl, String requestMethod, String postMessage) {
        String fullResourceURL = "http://192.168.1.160:8080/WebGMWORK/webresources" + resourceUrl;
        /**/
//        AsyncTask at = null;
//        synchronized (this) {
//            while (this.getStatus() != Status.FINISHED) {
//                try {
//                    this.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            at = this.execute(fullResourceURL, requestMethod, postMessage);
//
//            this.notify();
//
//        }
        /**/
        AsyncTask at = this.execute(fullResourceURL, requestMethod, postMessage);
        String serverResponse = null;
        try {
            serverResponse = (String) at.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return serverResponse;
    }





    private Object doGetRequest(String stringUrl) {
        BufferedReader reader = null;
        HttpURLConnection conn = null;
        URL url = null;
        Object serverResponseString = null;
        try {
            url = new URL(stringUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/json");

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "");
            }
            serverResponseString = sb.toString();
        } catch (Exception e) {
            Log.e("Error", "Error in doInBackground: " + e.getMessage());
        } finally {
            try {
                reader.close();
                conn.disconnect();
            } catch (Exception ex) {
                Log.e("Error", "Error while closing buffer: " + ex.getMessage());
            }
        }
        return serverResponseString;
    }



       /* Categoria cat = null;
        DateTime ultima = ultimaSubida;

        for (Object obj : map.get("Categoria")) {
            Categoria vista = (Categoria) obj;
            if (ultima.getFecha().compareTo(ultima) > 0) {
                switch (vista.getOp()) {
                    case "I":

                        break;
                    case "U":
                        Categoria catlocal = catDAO.filtrarCategoria(vista.getNombre());
                        catlocal.setNombre(vista.getNombre());
                        catlocal.setDescuento(vista.getDescuento());
                        catDAO.EditarCategoria(catlocal);
                        break;
                    case "D":
                        catDAO.removeCategoria(vista.getNombre());
                        break;
                }
            }
        }
        for (Object obj : map.get("Productos")) {
            ProductoVista vista = (ProductoVista) obj;
            if (vista.getFecha().compareTo(ultima) > 0) {
                switch (vista.getOp()) {
                    case "I":
                        //String nombre, double precio, byte[] img, boolean inhabilitats, double descuento
                        proDAO.addProducto(new Producto(vista.getNombre(), vista.getPrecio(), vista.getImg(), vista.isInhabilitats(), vista.getDescuento()));
                        break;
                    case "U":
                        Producto catlocal = proDAO.filtrarProducto(vista.getNombre());
                        catlocal.setNombre(vista.getNombre());
                        catlocal.setDescuento(vista.getDescuento());
                        catlocal.setImg(vista.getImg());
                        catlocal.setInhabilitats(vista.isInhabilitats());
                        catlocal.setPrecio(vista.getPrecio());
                        proDAO.EditarProducto(catlocal);
                        break;
                    case "D":
                        proDAO.removeProducto(vista.getNombre());
                        break;
                }
            }
        }
        for (Object obj : map.get("Usuario")) {
            UsuarioVista vista = (UsuarioVista) obj;
            if (vista.getFecha().compareTo(ultima) > 0) {
                switch (vista.getOp()) {
                    case "I":
                        //String nif, String nombre, String apellidos, String calle, String poblacion, boolean administrador, String username, String password
                        usuDAO.addUsuario(new Usuario(vista.getNif(), vista.getNombre(), vista.getApellidos(), vista.getCalle(), vista.getPoblacion(), vista.isAdministrador(), vista.getUsername(), vista.getPassword()));
                        break;
                    case "U":
                        Usuario catlocal = usuDAO.filtrarUsuario(vista.getNif());
                        catlocal.setNombre(vista.getNombre());
                        catlocal.setApellidos(vista.getApellidos());
                        catlocal.setCalle(vista.getCalle());
                        catlocal.setPoblacion(vista.getPoblacion());
                        catlocal.setAdministrador(vista.isAdministrador());
                        catlocal.setUsername(vista.getUsername());
                        catlocal.setPassword(vista.getPassword());
                        usuDAO.EditarProducto(catlocal);
                        break;
                    case "D":
                        usuDAO.removeUsuario(vista.getNombre());
                        break;
                }
            }
        }
        for (Object obj : map.get("Cliente")) {
            ClienteVista vista = (ClienteVista) obj;
            if (vista.getFecha().compareTo(ultima) > 0) {
                switch (vista.getOp()) {
                    case "I":
                        //String nif, String nombre, String apellidos, double longitud, double latitud, String calle, String poblacion, String proximaVisita                        usuDAO.addUsuario(new Usuario(vista.getNif(), vista.getNombre(), vista.getApellidos(), vista.getCalle(), vista.getPoblacion(), vista.isAdministrador(), vista.getUsername(), vista.getPassword()));
                        cliDAO.addCliente(new Cliente(vista.getNif(),vista.getNombre(),vista.getApellidos(),vista.getLongitud(),vista.getLatitud(),vista.getCalle(),vista.getPoblacion(),vista.getProximaVisita().toString()));
                        break;
                    case "U":
                        Cliente catlocal = cliDAO.filtrarCliente(vista.getNif());
                        catlocal.setNombre(vista.getNombre());
                        catlocal.setApellidos(vista.getApellidos());
                        catlocal.setCalle(vista.getCalle());
                        catlocal.setPoblacion(vista.getPoblacion());
                        catlocal.setLatitud(vista.getLatitud());
                        catlocal.setLongitud(vista.getLongitud());
                        catlocal.setCalle(vista.getCalle());
                        catlocal.setPoblacion(vista.getPoblacion());
                        catlocal.setProximaVisita(vista.getProximaVisita());
                        cliDAO.EditarCliente(catlocal);
                        break;
                    case "D":
                        cliDAO.removeCliente(vista.getNombre());
                        break;
                }
            }
        }
        for (Object obj : map.get("Pedido")) {
            PedidoVista vista = (PedidoVista) obj;
            if (vista.getFecha().compareTo(ultima) > 0) {
                switch (vista.getOp()) {
                    case "I":
                        //String fecha, String estado, double total
                        peDAO.addPedido(new Pedido(vista.getFechaEntrega(),vista.getEstado(),vista.getTotal()));
                    case "U":
                        Pedido catlocal = peDAO.filtrarPedido(vista.getId());
                        catlocal.setEstado(vista.getEstado());
                        catlocal.setFecha(vista.getFechaEntrega());
                        /*catlocal.setCalle(vista.getCalle());
                        catlocal.setPoblacion(vista.getPoblacion());
                        catlocal.setLatitud(vista.getLatitud());
                        catlocal.setLongitud(vista.getLongitud());
                        catlocal.setCalle(vista.getCalle());
                        catlocal.setPoblacion(vista.getPoblacion());
                        catlocal.setProximaVisita(vista.getProximaVisita());
                        peDAO.EditarPedido(catlocal);
                        break;
                    case "D":
                        peDAO.removePedido(vista.getId());
                        break;
                }
            }
        }
        for (Object obj : map.get("PedidoProducto")) {
            PedidoVista vista = (PedidoVista) obj;
            if (vista.getFecha().compareTo(ultima) > 0) {
                switch (vista.getOp()) {
                    case "I":
                        //Faltar
                        //String fecha, String estado, double total
                        // Producto producto, Pedido pedido, double cantidad
                        //peDAO.addPedido(new PedidoProducto(vista.getFechaEntrega(),vista.getEstado(),vista.getTotal()));
                    case "U":
                        Pedido catlocal = peDAO.filtrarPedido(vista.getId());
                        catlocal.setEstado(vista.getEstado());
                        catlocal.setFecha(vista.getFechaEntrega());
                        /*catlocal.setCalle(vista.getCalle());
                        catlocal.setPoblacion(vista.getPoblacion());
                        catlocal.setLatitud(vista.getLatitud());
                        catlocal.setLongitud(vista.getLongitud());
                        catlocal.setCalle(vista.getCalle());
                        catlocal.setPoblacion(vista.getPoblacion());
                        catlocal.setProximaVisita(vista.getProximaVisita());
                        peDAO.EditarPedido(catlocal);
                        break;
                    case "D":
                        peDAO.removePedido(vista.getId());
                        break;
                }
            }*/
    //    return null;
    //}
    private Object doPostRequest(String stringUrl, String postMessage) {
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
        HttpResponse response;
        String stringResponse = null;
        try {
            HttpPost post = new HttpPost(new URI(stringUrl));
            StringEntity se = new StringEntity(postMessage);
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(se);
            response = client.execute(post);
            if (response != null) {
                InputStream in = response.getEntity().getContent();
                stringResponse = getStringFromInputStream(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringResponse;
    }
    private String getStringFromInputStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedInputStream bis = new BufferedInputStream(is);
            InputStreamReader isr = new InputStreamReader(bis);
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {

        }
        return sb.toString();
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(Object aVoid) {
        super.onPostExecute(aVoid);
    }
}
