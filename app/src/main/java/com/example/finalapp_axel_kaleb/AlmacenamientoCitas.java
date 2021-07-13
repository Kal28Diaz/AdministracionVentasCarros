package com.example.finalapp_axel_kaleb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AlmacenamientoCitas {
    public static AlmacenamientoCitas mAlmacenamientoCitas;
    private Context mContexto;
    private SQLiteDatabase mBaseDeDatos;

    //Funcion para crear el almacenamiento de Citas
    public static AlmacenamientoCitas ObtenerAlmacenamientoCitas(Context context){
        if( mAlmacenamientoCitas == null){
            mAlmacenamientoCitas = new AlmacenamientoCitas(context);
        }
        return  mAlmacenamientoCitas;
    }

    //Constructor
    private AlmacenamientoCitas(Context context){
        mContexto = context.getApplicationContext();
        mBaseDeDatos = new ConexionSQLiteHelper(mContexto).getWritableDatabase();   //Se crea la Base de Datos
    }

    //Obtiene los valores a insertar en la Base de Datos en la tabla "Cita"
    private static ContentValues ObtenerContentValues(Cita cita){
        ContentValues valores = new ContentValues();
        valores.put(BaseDeDatos.Cita.Columnas.UUID, cita.getmID().toString());
        valores.put(BaseDeDatos.Cita.Columnas.TITULO, cita.getTitulo());
        valores.put(BaseDeDatos.Cita.Columnas.FECHA, cita.getFecha());
        valores.put(BaseDeDatos.Cita.Columnas.HORA, cita.getHora());
        valores.put(BaseDeDatos.Cita.Columnas.ID_TRAMITE, cita.getmIDTramite());
        return valores;
    }

    //Insert
    public void AgregarCita(Cita cita){
        ContentValues valores = ObtenerContentValues(cita);
        mBaseDeDatos.insert(BaseDeDatos.Cita.NOMBRE, null, valores);
    }

    //Delete
    public void EliminarCita(Cita cita){
        String CitaUUID = cita.getmID().toString();
        mBaseDeDatos.delete(BaseDeDatos.Cita.NOMBRE, BaseDeDatos.Cita.Columnas.UUID + " = ?", new String[] {CitaUUID});
    }

    //Update
    public void ActualizarCita(Cita cita){
        String CitaUUID = cita.getmID().toString();
        ContentValues valores = ObtenerContentValues(cita);
        mBaseDeDatos.update(BaseDeDatos.Cita.NOMBRE, valores, BaseDeDatos.Cita.Columnas.UUID + " = ?", new String[] {CitaUUID});
    }

    //Cursor para obtener los datos de la cita
    private CursorWrapper queryCitas(String whereClause, String[] whereArgs){
        Cursor cursor = mBaseDeDatos.query(
                BaseDeDatos.Cita.NOMBRE,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new CursorWrapperCitas(cursor);
    }

    //Funcion para obtener la lista de citas de un tramite en cuestion
    public List<Cita> ObtenerListaCitas(UUID idTramite){
        List<Cita> citas = new ArrayList<>();
        CursorWrapperCitas cursor = (CursorWrapperCitas) queryCitas(BaseDeDatos.Cita.Columnas.ID_TRAMITE+" = ?", new String[] {idTramite.toString()});
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                citas.add(cursor.obtenerCita());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return citas;
    }

    //Funcion para obtener una cita en especifico
    public Cita ObtenerCita(UUID id){
        CursorWrapperCitas cursor = (CursorWrapperCitas) queryCitas(
                BaseDeDatos.Cita.Columnas.UUID + " = ?",
                new String[] {id.toString()}
        );

        try{
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.obtenerCita();
        }
        finally {
            cursor.close();
        }
    }
}
