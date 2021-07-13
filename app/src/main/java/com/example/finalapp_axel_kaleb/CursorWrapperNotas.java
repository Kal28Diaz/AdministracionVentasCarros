package com.example.finalapp_axel_kaleb;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

public class CursorWrapperNotas extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CursorWrapperNotas(Cursor cursor) {
        super(cursor);
    }

    public Nota obtenerNota(){
        //Se lee la informacion de la Tabla Nota de la Base de Datos
        String uuidstring = getString(getColumnIndex(BaseDeDatos.Nota.Columnas.UUID));
        String titulo = getString(getColumnIndex(BaseDeDatos.Nota.Columnas.TITULO));
        String cuerpo = getString(getColumnIndex(BaseDeDatos.Nota.Columnas.CUERPO));
        String uuidtramite = getString(getColumnIndex(BaseDeDatos.Nota.Columnas.ID_TRAMITE));

        //Se asigna la informacion recopilada de la Base de Datos a un objeto tipo Nota
        Nota nota = new Nota(UUID.fromString(uuidstring));
        nota.setTitulo(titulo);
        nota.setCuerpo(cuerpo);
        nota.setmIDTramite(uuidtramite);

        return nota;
    }
}
