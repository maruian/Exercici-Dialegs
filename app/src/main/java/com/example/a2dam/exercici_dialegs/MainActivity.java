package com.example.a2dam.exercici_dialegs;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Date;
import java.util.Timer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private Button botoData;
    private Button botoHora;
    private Button botoColor;
    private TextView textoFecha, textoHora, textoColor;
    private DatePickerDialog dpDialog;
    private TimePickerDialog tpDialog;
    private AlertDialog.Builder adBuilder;
    private AlertDialog alertDialog;
    private int estaDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        estaDialog = 0;

        botoData = (Button)findViewById(R.id.botoData);
        botoHora = (Button)findViewById(R.id.botoHora);
        botoColor = (Button)findViewById(R.id.botoColor);

        textoFecha = (TextView)findViewById(R.id.textData);
        textoHora = (TextView)findViewById(R.id.textHora);
        textoColor = (TextView)findViewById(R.id.textColor);

        botoData.setOnClickListener(this);
        botoHora.setOnClickListener(this);
        botoColor.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.botoData:
                Calendar calendar = Calendar.getInstance();
                int anyo = calendar.get(Calendar.YEAR);
                int mes = calendar.get(Calendar.MONTH);
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                dpDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        textoFecha.setText("DATA: "+i2+"/"+(i1+1)+"/"+i);
                    }
                }, anyo,mes,dia);
                estaDialog = 1;
                dpDialog.show();
                break;
            case R.id.botoHora:

                //vore si em pot tornar l'hora actual
                int horas = 0;
                int minutos = 0;
                tpDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1){
                        textoHora.setText("HORA: "+i+":"+(i1<10?"0"+i1:i1));
                    }
                }, horas, minutos, true);
                estaDialog = 2;
                tpDialog.show();
                break;
            case R.id.botoColor:
                adBuilder = new AlertDialog.Builder(this);
                adBuilder
                        .setTitle("Tria un color")
                        .setItems(R.array.opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i){
                                    case 0:
                                        textoColor.setText("COLOR: Roig");
                                        textoColor.setTextColor(Color.RED);
                                        break;
                                    case 1:
                                        textoColor.setText("COLOR: Blau");
                                        textoColor.setTextColor(Color.BLUE);
                                        break;
                                    case 2:
                                        textoColor.setText("COLOR: Verd");
                                        textoColor.setTextColor(Color.GREEN);
                                        break;
                                }
                            }
                        });
                alertDialog = adBuilder.create();
                estaDialog = 3;
                alertDialog.show();
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("DATA", textoFecha.getText().toString());
        outState.putCharSequence("HORA", textoHora.getText().toString());
        outState.putCharSequence("COLOR", textoColor.getText().toString());
        if (estaDialog == 1){
            int any = dpDialog.getDatePicker().getYear();
            int mes = dpDialog.getDatePicker().getMonth();
            int dia = dpDialog.getDatePicker().getDayOfMonth();
            outState.putInt("ANY",any);
            outState.putInt("MES",mes);
            outState.putInt("DIA",dia);
        } else if (estaDialog == 2){
            //salvar les dades del rellotge

        }
        outState.putInt("ESTADIALOG",estaDialog);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textoFecha.setText(savedInstanceState.getCharSequence("DATA"));
        textoHora.setText(savedInstanceState.getCharSequence("HORA"));
        textoColor.setText(savedInstanceState.getCharSequence("COLOR"));
        switch (textoColor.getText().toString()){
            case "COLOR: Roig": textoColor.setTextColor(Color.RED);
                break;
            case "COLOR: Blau": textoColor.setTextColor(Color.BLUE);
                break;
            case "COLOR: Verd": textoColor.setTextColor(Color.GREEN);
                break;
        }

        int i = savedInstanceState.getInt("ESTADIALOG");
        switch (i){
            case 1:
                // recuperar dades de la data

                int anyo =
                dpDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        textoFecha.setText("DATA: "+i2+"/"+(i1+1)+"/"+i);
                    }
                }, anyo,mes,dia);
                break;
            case 2:
                tpDialog.show();
                break;
            case 3:
                alertDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        // guardar hora i minuts com atributs de la classe
    }
}
