package com.example.finalapp_axel_kaleb;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class ActividadListaCitas extends SingleFragment {

    public static final String ID_DEL_TRAMITE_C = "id_del_tramite_c";  //Etiqueta para pasar por medio del Intent el ID del tramite correspondiente a las citas
    UUID mTramiteID;                                                   //Almacena el ID de dicho tramite


    //Retorno de Fragmento:
    @Override
    protected Fragment CrearFragmento() {
        mTramiteID = (UUID) getIntent().getSerializableExtra(ID_DEL_TRAMITE_C);     //Se obtiene el ID del tramite proveniente del Intent
        /*Manda a llamar a la lista de citas filtrando por el ID del
        * tramite en cuestion (no mostrará citas cuyo valor del FK
        * del ID del tramite sea diferente al proporcionado).*/
        return FragmentoListaCitas.newInstance(mTramiteID);
    }


    /*Intent de ActividadListaCitas, sus parametros son:
    * tramiteID: Contiene el ID del tramite desde el que fue tapeado el boton de "Administrar citas*/
    public static Intent newIntentCita (Context packageContext, UUID tramiteID){
        Intent intent = new Intent(packageContext, ActividadListaCitas.class);
        intent.putExtra(ID_DEL_TRAMITE_C, tramiteID);
        return intent;
    }

}
