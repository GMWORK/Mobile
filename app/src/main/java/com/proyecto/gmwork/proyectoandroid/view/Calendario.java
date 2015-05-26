package com.proyecto.gmwork.proyectoandroid.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;

import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.controller.PersistencyController;
import com.proyecto.gmwork.proyectoandroid.view.Cliente.UIListaClienteView;

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
    private String fechaSeleccionada;
    private String cli;
    private boolean dismiss;
    private PersistencyController per;
    public Calendario(Context con, TextView v) {
        myCalendar = Calendar.getInstance();
        this.con = con;
        new DatePickerDialog(con, this, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        this.v = v;

    }

    public Calendario(Context con, PersistencyController per , String cli) {
        myCalendar = Calendar.getInstance();
        this.per = per;
        this.cli = cli;
        this.con = con;
        new DatePickerDialog(con, this, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        if(v != null){
        v.setText(darFecha());
        }
        setFecha();

    }

    public String getFechaSeleccionada() {
        dismiss =true;
        return fechaSeleccionada;
    }

    public void setFechaSeleccionada(String fechaSeleccionada) {
        this.fechaSeleccionada = fechaSeleccionada;
    }

    public void setFecha() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
        this.fechaSeleccionada = sdf.format(myCalendar.getTime());

        per.actualizarFechaCliente(cli, fechaSeleccionada);
    }

    public String darFecha() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
        return sdf.format(myCalendar.getTime());

    }

    public boolean isDismiss() {
        return dismiss;
    }

    public void setDismiss(boolean dismiss) {
        this.dismiss = dismiss;
    }

    private TextView v;

}
