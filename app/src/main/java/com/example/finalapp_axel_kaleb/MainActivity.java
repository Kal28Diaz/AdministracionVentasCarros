package com.example.finalapp_axel_kaleb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button botonProspecto, botonLista, botonReporte, botonVehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_inicio);

        //Wiring Up de los botones
        botonProspecto = findViewById(R.id.botonProspecto);
        botonLista = findViewById(R.id.botonLista);
        botonReporte = findViewById(R.id.botonReporte);
        botonVehiculo = findViewById(R.id.botonVehiculo);


        //Al presionar el boton de "Capturar nuevo prospecto"
        botonProspecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tramite tramite = new Tramite();        //Se crea el nuevo tramite
                AlmacenamientoTramites.ObtenerAlmacenamientoTramites(botonProspecto.getContext()).AgregarTramite(tramite);  //Se guarda el tramite creado en la BD
                Intent intent = ActividadCliente.newIntent(botonProspecto.getContext(), tramite.getmID(), true);
                startActivity(intent);
            }
        });

        //Al presionar el boton de "Ver lista de tramites"
        botonLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(botonLista.getContext(), ActividadListaTramites.class);
                startActivity(intent);
            }
        });

        //Al presionar el boton de "Generar reporte"
        botonReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(botonReporte.getContext(), ActividadListaReporte.class);
                startActivity(intent);
            }
        });

        //Al presionar el boton de "Administrar vehiculos"
        botonVehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(botonVehiculo.getContext(), ActividadListaVehiculos.class);
                startActivity(intent);
            }
        });
    }


}