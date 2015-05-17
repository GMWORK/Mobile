package com.proyecto.gmwork.proyectoandroid.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Matthew on 17/05/2015.
 */
public class Calendario implements DatePickerDialog.OnDateSetListener {
    private Calendar myCalendar;
    private Context con;

    public Calendario(Context con,TextView v) {
        myCalendar = Calendar.getInstance();
        this.con = con;
        new DatePickerDialog(con, this, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        this.v = v;

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        v.setText(darFecha());


    }

    public String darFecha() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
        return sdf.format(myCalendar.getTime());

    }
    private TextView v;

}
