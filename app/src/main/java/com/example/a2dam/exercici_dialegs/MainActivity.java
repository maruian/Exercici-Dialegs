package com.example.a2dam.exercici_dialegs;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.icu.util.GregorianCalendar;
import android.icu.util.Calendar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnClickListener {

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


        Calendar calendar = new GregorianCalendar(android.icu.util.TimeZone.getTimeZone("Europe/Madrid"));


        crearCalendario(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        crearReloj(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        crearAlertDialog();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.botoData:
                // mostrem el calendari
                dpDialog.show();
                break;
            case R.id.botoHora:
                // mostrem el rellotge
                tpDialog.show();
                break;
            case R.id.botoColor:
                // mostrem el alert dialog
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

        //comprovem quin dialeg s'esta mostrant
        if (dpDialog.isShowing())
            estaDialog=1;
        if (tpDialog.isShowing())
            estaDialog=2;
        if (alertDialog.isShowing())
            estaDialog=3;

        // guardem les dades adients segons el que es mostre
        if (estaDialog == 1){
            int any = dpDialog.getDatePicker().getYear();
            int mes = dpDialog.getDatePicker().getMonth();
            int dia = dpDialog.getDatePicker().getDayOfMonth();
            outState.putInt("ANY",any);
            outState.putInt("MES",mes);
            outState.putInt("DIA",dia);
        } else if (estaDialog == 2){
            outState.putBundle("TIMEPICKERDIALOG",tpDialog.onSaveInstanceState());
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

        switch (savedInstanceState.getInt("ESTADIALOG")){
            case 1:
                // recuperar dades de la data
                // instanciem de nou el DatePickerDialog
                crearCalendario(savedInstanceState.getInt("ANY"),savedInstanceState.getInt("MES"),savedInstanceState.getInt("DIA"));
                dpDialog.show();
                break;
            case 2:
                // recuperem el timepickerdialog
                tpDialog.onRestoreInstanceState(savedInstanceState.getBundle("TIMEPICKERDIALOG"));
                break;
            case 3:
                // instanciem de nou el AlertDialog
                crearAlertDialog();
                alertDialog.show();
                break;
            default:
                break;
        }
    }


    private void crearCalendario(int anyo, int mes, int dia){
        dpDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                textoFecha.setText("DATA: "+i2+"/"+(i1+1)+"/"+i);
            }
        }, anyo,mes,dia);
    }

    private void crearReloj(int hora, int minutos){
        tpDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1){
                textoHora.setText("HORA: "+(i<10?"0"+i:i)+":"+(i1<10?"0"+i1:i1));
            }
        }, hora, minutos, true);
    }

    private void crearAlertDialog(){
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
    }


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        Toast.makeText(this,i+"",Toast.LENGTH_SHORT).show();
    }
}
