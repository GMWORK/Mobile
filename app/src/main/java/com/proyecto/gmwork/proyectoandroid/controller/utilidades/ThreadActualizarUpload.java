package com.proyecto.gmwork.proyectoandroid.controller.utilidades;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

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
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Mateo on 23/05/2015.
 */
public class ThreadActualizarUpload extends AsyncTask<String, Void, Void> {
    private String url = "http://10.0.2.2:8080/WebGMWORK/webresources/";
    private TreeMap<String, List<String[]>> map;
    private ProgressDialog dialog;

    public ThreadActualizarUpload(TreeMap<String, List<String[]>> map, Context con) {
        dialog = new ProgressDialog(con);
        this.map = map;

    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            doOperation();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            return null;
        }


    }

    public void doOperation() {
        String[] list = null;
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            List<String[]> lista = (List<String[]>) map.get(key);
            int i = 0;

            list = lista.get(i);
            switch (list[0]) {
                case "POST":
                    doOperationPost(list[1], list[2]);
                    break;
                case "PUT":
                    doOperationPut(list[1], list[2]);
                    break;
                case "DELETE":
                    doOperationDelete(list[1], list[2]);
                    break;
            }
            i++;
        }


    }


    private void doOperationDelete(String s, String s1) {

        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
        HttpResponse response;
        StringBuilder stringURL = new StringBuilder();
        stringURL.append(url).append(s).append(s1);
        String stringResponse = null;
        try {
            HttpDelete delete = new HttpDelete(new URI(stringURL.toString()));
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


    }

    private void doOperationPut(String s, String s1) {

        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
        HttpResponse response;
        String stringResponse = null;
        StringBuilder stringURL = new StringBuilder();
        stringURL.append(url).append(s);
        try {
            HttpPut put = new HttpPut(new URI(stringURL.toString()));
            StringEntity se = new StringEntity(s1);
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
    }

    private void doOperationPost(String ruta, String json) {
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
        HttpResponse response;
        String stringResponse = null;
        StringBuilder stringURL = new StringBuilder();
        stringURL.append(url).append(ruta);

        try {
            HttpPost post = new HttpPost(new URI(stringURL.toString()));
            StringEntity se = new StringEntity(json);
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
            //LogAndToastMaker.makeErrorLog(e.getMessage());
        }
        return sb.toString();
    }

    @Override
    protected void onPreExecute() {


        dialog.setMessage("Please wait...");
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        dialog.dismiss();
        super.onPostExecute(aVoid);
    }


}
