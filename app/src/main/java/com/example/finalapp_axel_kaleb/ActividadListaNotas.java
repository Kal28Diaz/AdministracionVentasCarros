package com.example.finalapp_axel_kaleb;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class ActividadListaNotas extends SingleFragment {

    public static final String ID_DEL_TRAMITE_N = "id_del_tramite_n";  //Etiqueta para pasar por medio del Intent el ID del tramite correspondiente a las notas
    UUID mTramiteID;                                                   //Almacena el ID de dicho tramite

    
    //Retorno de Fragmento:
    @Override
    protected Fragment CrearFragmento() {
        mTramiteID = (UUID) getIntent().getSerializableExtra(ID_DEL_TRAMITE_N);      //Se obtiene el ID del tramite proveniente del Intent
        /*Manda a llamar a la lista de notas filtrando por el ID del
         * tramite en cuestion (no mostrará notas cuyo valor del FK
         * del ID del tramite sea diferente al proporcionado).*/
        return FragmentoListaNotas.newInstance(mTramiteID);
    }


    /*Intent de ActividadListaNotas, sus parametros son:
     * tramiteID: Contiene el ID del tramite desde el que fue tapeado el boton de "Administrar notas*/
    public static Intent newIntentNota (Context packageContext, UUID tramiteID){
        Intent intent = new Intent(packageContext, ActividadListaNotas.class);
        intent.putExtra(ID_DEL_TRAMITE_N, tramiteID);
        return intent;
    }
}
