package com.example.finalapp_axel_kaleb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AlmacenamientoNotas {

    public static AlmacenamientoNotas mAlmacenamientoNotas;
    private Context mContexto;
    private SQLiteDatabase mBaseDeDatos;

    //Funcion para crear el almacenamiento de Notas
    public static AlmacenamientoNotas ObtenerAlmacenamientoNotas(Context context){
        if( mAlmacenamientoNotas == null){
            mAlmacenamientoNotas = new AlmacenamientoNotas(context);
        }
        return  mAlmacenamientoNotas;
    }

    //Constructor
    private AlmacenamientoNotas(Context context){
        mContexto = context.getApplicationContext();
        mBaseDeDatos = new ConexionSQLiteHelper(mContexto).getWritableDatabase();   //Se crea la Base de Datos
    }

    //Obtiene los valores a insertar en la Base de Datos en la tabla "Nota"
    private static ContentValues ObtenerContentValues(Nota nota){
        ContentValues valores = new ContentValues();
        valores.put(BaseDeDatos.Nota.Columnas.UUID, nota.getmID().toString());
        valores.put(BaseDeDatos.Nota.Columnas.TITULO, nota.getTitulo());
        valores.put(BaseDeDatos.Nota.Columnas.CUERPO, nota.getCuerpo());
        valores.put(BaseDeDatos.Nota.Columnas.ID_TRAMITE, nota.getmIDTramite());
        return valores;
    }

    //Insert
    public void AgregarNota(Nota nota){
        ContentValues valores = ObtenerContentValues(nota);
        mBaseDeDatos.insert(BaseDeDatos.Nota.NOMBRE, null, valores);
    }

    //Delete
    public void EliminarNota(Nota nota){
        String NotaUUID = nota.getmID().toString();
        mBaseDeDatos.delete(BaseDeDatos.Nota.NOMBRE, BaseDeDatos.Nota.Columnas.UUID + " = ?", new String[] {NotaUUID});
    }

    //Update
    public void ActualizarNota(Nota nota){
        String NotaUUID = nota.getmID().toString();
        ContentValues valores = ObtenerContentValues(nota);
        mBaseDeDatos.update(BaseDeDatos.Nota.NOMBRE, valores, BaseDeDatos.Nota.Columnas.UUID + " = ?", new String[] {NotaUUID});
    }

    //Cursor para obtener los datos de la nota
    private CursorWrapper queryNotas(String whereClause, String[] whereArgs){
        Cursor cursor = mBaseDeDatos.query(
                BaseDeDatos.Nota.NOMBRE,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new CursorWrapperNotas(cursor);
    }

    //Funcion para obtener la lista de notas de un tramite en cuestion
    public List<Nota> ObtenerListaNotas(UUID idTramite){
        List<Nota> notas = new ArrayList<>();
        CursorWrapperNotas cursor = (CursorWrapperNotas) queryNotas(BaseDeDatos.Nota.Columnas.ID_TRAMITE+" = ?", new String[] {idTramite.toString()});
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                notas.add(cursor.obtenerNota());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return notas;
    }

    //Funcion para obtener una nota en especifico
    public Nota ObtenerNota(UUID id){
        CursorWrapperNotas cursor = (CursorWrapperNotas) queryNotas(
                BaseDeDatos.Nota.Columnas.UUID + " = ?",
                new String[] {id.toString()}
        );

        try{
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.obtenerNota();
        }
        finally {
            cursor.close();
        }
    }
}
