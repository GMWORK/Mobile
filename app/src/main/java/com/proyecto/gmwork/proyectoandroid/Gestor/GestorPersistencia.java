package com.proyecto.gmwork.proyectoandroid.Gestor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mateo on 30/04/15.
 */
public class GestorPersistencia  extends SQLiteOpenHelper {
    public GestorPersistencia(Context context) {
        super(context, "gmwork.db", null, 00001);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
