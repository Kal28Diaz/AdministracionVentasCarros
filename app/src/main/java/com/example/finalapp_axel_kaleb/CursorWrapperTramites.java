package com.example.finalapp_axel_kaleb;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

public class CursorWrapperTramites extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CursorWrapperTramites(Cursor cursor) {
        super(cursor);
    }

    public Tramite obtenerTramite(){
        //Se lee la informacion de la Tabla Tramite de la Base de Datos
        String uuidString = getString(getColumnIndex(BaseDeDatos.Tramite.Columnas.UUID));
        String actividad = getString(getColumnIndex(BaseDeDatos.Tramite.Columnas.ACTIVIDAD));
        String perfil_vehiculo = getString(getColumnIndex(BaseDeDatos.Tramite.Columnas.PERFIL_VEHICULO));
        String etapa = getString(getColumnIndex(BaseDeDatos.Tramite.Columnas.ETAPA));
        String financiamiento = getString(getColumnIndex(BaseDeDatos.Tramite.Columnas.FINANCIAMIENTO));
        String uuidClienteString = getString(getColumnIndex(BaseDeDatos.Tramite.Columnas.ID_CLIENTE));
        String uuidVehiculoString = getString(getColumnIndex(BaseDeDatos.Tramite.Columnas.ID_VEHICULO));

        //Se asigna la informacion recopilada de la Base de Datos a un objeto tipo Tramite
        Tramite tramite = new Tramite(UUID.fromString(uuidString));
        tramite.setActividad(actividad);
        tramite.setPerfil_Vehiculo(perfil_vehiculo);
        tramite.setEtapa(etapa);
        tramite.setFinanciamiento(financiamiento);
        tramite.setmIDCliente(uuidClienteString);
        tramite.setmIDVehiculo(uuidVehiculoString);

        return tramite;
    }
}
