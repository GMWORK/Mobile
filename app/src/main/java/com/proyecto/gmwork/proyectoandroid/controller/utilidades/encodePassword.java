package com.proyecto.gmwork.proyectoandroid.controller.utilidades;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by Mateo on 25/05/2015.
 */
public class encodePassword {
    public static String encodePass(String password) {
        String passwordEncode = "";
        byte[] data = null;
        try {
            data = password.getBytes("UTF-8");
            passwordEncode = Base64.encodeToString(data, Base64.DEFAULT).trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return passwordEncode;

    }

    public  static String decodePassword(String password) {
        String passwordDecode = "";
        byte[] data = null;
        try {
            data = Base64.decode(password, Base64.DEFAULT);
            passwordDecode = new String(data, "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return passwordDecode;

    }

}
