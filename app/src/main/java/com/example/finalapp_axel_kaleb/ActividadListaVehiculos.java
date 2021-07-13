package com.example.finalapp_axel_kaleb;

import androidx.fragment.app.Fragment;

public class ActividadListaVehiculos extends SingleFragment {

    //Retorno de Fragmento:
    @Override
    protected Fragment CrearFragmento() {
        /*Manda a llamar a la lista de vehiculos*/
        return new FragmentoListaVehiculos();
    }
}
