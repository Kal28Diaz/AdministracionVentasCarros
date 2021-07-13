package com.example.finalapp_axel_kaleb;

import androidx.fragment.app.Fragment;

public class ActividadListaTramites extends SingleFragment {

    //Retorno de Fragmento:
    @Override
    protected Fragment CrearFragmento() {
        /*Manda a llamar a la lista de tramites*/
        return new FragmentoListaTramites();
    }
}
