package com.example.finalapp_axel_kaleb;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class SingleFragment extends AppCompatActivity {

    protected abstract Fragment CrearFragmento();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contenedor_fragmento);

        FragmentManager fm = getSupportFragmentManager();     //La actividad tendra soporte para fragmentos

        Fragment fragmento = fm.findFragmentById(R.id.contenedor_fragmento_vehiculos);

        if(fragmento == null){
            fragmento = CrearFragmento();
            fm.beginTransaction().add(R.id.contenedor_fragmento_vehiculos,fragmento).commit();
        }
    }

}
