package com.example.finalapp_axel_kaleb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AlmacenamientoTramites {

    private static AlmacenamientoTramites mAlmacenamientoTramites;
    private Context mContexto;
    private SQLiteDatabase mBaseDeDatos;

    //Funcion para crear el almacenamiento de Tramites
    public static AlmacenamientoTramites ObtenerAlmacenamientoTramites(Context context){
        if(mAlmacenamientoTramites == null){
            mAlmacenamientoTramites = new AlmacenamientoTramites(context);
        }
        return mAlmacenamientoTramites;
    }

    //Constructor
    private AlmacenamientoTramites(Context context){
        mContexto = context.getApplicationContext();
        mBaseDeDatos = new ConexionSQLiteHelper(mContexto).getWritableDatabase();   //Se crea la Base de Datos
    }

    //Obtiene los valores a insertar en la Base de Datos en la tabla "Tramite"
    private static ContentValues ObtenerContentValues(Tramite tramite){
        ContentValues valores = new ContentValues();
        valores.put(BaseDeDatos.Tramite.Columnas.UUID, tramite.getmID().toString());
        valores.put(BaseDeDatos.Tramite.Columnas.ACTIVIDAD, tramite.getActividad());
        valores.put(BaseDeDatos.Tramite.Columnas.PERFIL_VEHICULO, tramite.getPerfil_Vehiculo());
        valores.put(BaseDeDatos.Tramite.Columnas.ETAPA, tramite.getEtapa());
        valores.put(BaseDeDatos.Tramite.Columnas.FINANCIAMIENTO, tramite.getFinanciamiento());
        valores.put(BaseDeDatos.Tramite.Columnas.ID_CLIENTE, tramite.getmIDCliente());
        valores.put(BaseDeDatos.Tramite.Columnas.ID_VEHICULO, tramite.getmIDVehiculo());
        return valores;
    }

    //Insert
    public void AgregarTramite(Tramite tramite){
        ContentValues valores = ObtenerContentValues(tramite);
        mBaseDeDatos.insert(BaseDeDatos.Tramite.NOMBRE, null, valores);
    }

    //Delete
    public void EliminarTramite(Tramite tramite){
        String TramiteUUID = tramite.getmID().toString();
        mBaseDeDatos.delete(BaseDeDatos.Tramite.NOMBRE, BaseDeDatos.Tramite.Columnas.UUID + " = ?", new String[] {TramiteUUID});
    }

    //Update
    public void ActualizarTramite(Tramite tramite){
        String TramiteUUID = tramite.getmID().toString();
        ContentValues valores = ObtenerContentValues(tramite);
        mBaseDeDatos.update(BaseDeDatos.Tramite.NOMBRE, valores, BaseDeDatos.Tramite.Columnas.UUID + " = ?", new String[] {TramiteUUID});
    }

    //Cursor para obtener los datos del Tramite
    private CursorWrapper queryTramites(String whereClause, String[] whereArgs){
        Cursor cursor = mBaseDeDatos.query(
                BaseDeDatos.Tramite.NOMBRE,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new CursorWrapperTramites(cursor);
    }

    //Funcion para obtener la lista de tramites
    public List<Tramite> ObtenerListaTramites(){
        List<Tramite> tramites = new ArrayList<>();
        CursorWrapperTramites cursor = (CursorWrapperTramites) queryTramites(null, null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                tramites.add(cursor.obtenerTramite());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return tramites;
    }

    //Funcion para obtener un tramite en especifico
    public Tramite ObtenerTramite(UUID id){
        CursorWrapperTramites cursor = (CursorWrapperTramites) queryTramites(
                BaseDeDatos.Tramite.Columnas.UUID + " = ?",
                new String[] {id.toString()}
        );

        try{
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.obtenerTramite();
        }
        finally {
            cursor.close();
        }
    }

}
