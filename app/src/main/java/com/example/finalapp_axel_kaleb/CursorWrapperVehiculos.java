package com.example.finalapp_axel_kaleb;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;

import java.util.Date;
import java.util.UUID;

public class CursorWrapperVehiculos extends CursorWrapper{

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CursorWrapperVehiculos(Cursor cursor) {
        super(cursor);
    }

    public Vehiculo obtenerVehiculo(){
        //Se lee la informacion de la Tabla Vehiculo de la Base de Datos
        String uuidString = getString(getColumnIndex(BaseDeDatos.Vehiculo.Columnas.UUID));
        String modelo = getString(getColumnIndex(BaseDeDatos.Vehiculo.Columnas.MODELO));
        String version = getString(getColumnIndex(BaseDeDatos.Vehiculo.Columnas.VERSION));
        String transmision = getString(getColumnIndex(BaseDeDatos.Vehiculo.Columnas.TRANSMISION));
        String estado = getString(getColumnIndex(BaseDeDatos.Vehiculo.Columnas.ESTADO));
        String año = getString(getColumnIndex(BaseDeDatos.Vehiculo.Columnas.AÑO));

        //Se asigna la informacion recopilada de la Base de Datos a un objeto tipo Vehiculo
        Vehiculo vehiculo = new Vehiculo(UUID.fromString(uuidString));
        vehiculo.setModelo(modelo);
        vehiculo.setVersion(version);
        vehiculo.setTransmision(transmision);
        vehiculo.setEstado(estado);
        vehiculo.setAño(año);

        //Se retorna el objeto tipo Vehiculo
        return vehiculo;
    }
}
