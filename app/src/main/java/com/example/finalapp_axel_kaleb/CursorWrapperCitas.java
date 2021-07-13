package com.example.finalapp_axel_kaleb;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

public class CursorWrapperCitas extends CursorWrapper {

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CursorWrapperCitas(Cursor cursor) {
        super(cursor);
    }

    public Cita obtenerCita(){
        //Se lee la informacion de la Tabla Cita de la Base de Datos
        String uuidstring = getString(getColumnIndex(BaseDeDatos.Cita.Columnas.UUID));
        String titulo = getString(getColumnIndex(BaseDeDatos.Cita.Columnas.TITULO));
        String fecha = getString(getColumnIndex(BaseDeDatos.Cita.Columnas.FECHA));
        String hora = getString(getColumnIndex(BaseDeDatos.Cita.Columnas.HORA));
        String uuidtramite = getString(getColumnIndex(BaseDeDatos.Cita.Columnas.ID_TRAMITE));

        //Se asigna la informacion recopilada de la Base de Datos a un objeto tipo Nota
        Cita cita = new Cita(UUID.fromString(uuidstring));
        cita.setTitulo(titulo);
        cita.setFecha(fecha);
        cita.setHora(hora);
        cita.setmIDTramite(uuidtramite);

        return cita;
    }
}
