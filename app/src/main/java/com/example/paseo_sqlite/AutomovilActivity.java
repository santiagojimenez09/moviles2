package com.example.paseo_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AutomovilActivity extends AppCompatActivity {

    TextView jtvnombre;
    EditText jetplaca,jetmarca,jetmodelo,jetvalor;
    Button  jbtguardar,jbtconsultar,jbteliminar,jbtcancelar,jbtregresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automovil);

        getSupportActionBar().hide();

        /*jetplaca.findViewById(R.id.etplaca);
        jetmarca.findViewById(R.id.etmarca);
        jetmodelo.findViewById(R.id.etmodelo);
        jetvalor.findViewById(R.id.etvalor);
        jbtguardar.findViewById(R.id.btguardar);
        jbtconsultar.findViewById(R.id.btconsultar);
        jbteliminar.findViewById(R.id.bteliminar);
        jbtcancelar.findViewById(R.id.btcancelar);
        jbtregresar.findViewById(R.id.btregresar);*/
        jtvnombre=findViewById(R.id.tvnombre);
        String nombre;
        nombre=getIntent().getStringExtra("Dato");
        jtvnombre.setText(nombre);



    }
}