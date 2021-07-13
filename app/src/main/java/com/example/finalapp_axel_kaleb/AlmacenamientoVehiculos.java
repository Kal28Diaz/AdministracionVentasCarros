package com.example.finalapp_axel_kaleb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AlmacenamientoVehiculos {

    private static AlmacenamientoVehiculos malmacenamientoVehiculos;
    private Context mContexto;
    private SQLiteDatabase mBaseDeDatos;

    //Funcion para crear el almacenamiento de vehiculos
    public static AlmacenamientoVehiculos ObtenerAlmacenamientoVehiculos(Context context){
        if(malmacenamientoVehiculos == null){
            malmacenamientoVehiculos = new AlmacenamientoVehiculos(context);
        }
        return malmacenamientoVehiculos;
    }

    //Constructor
    private AlmacenamientoVehiculos(Context context){
        mContexto = context.getApplicationContext();
        mBaseDeDatos = new ConexionSQLiteHelper(mContexto).getWritableDatabase();   //Se crea la Base de Datos
    }

    //Obtiene los valores a insertar en la Base de Datos en la tabla "Vehiculos"
    private static ContentValues ObtenerContentValues(Vehiculo vehiculo){
        ContentValues valores = new ContentValues();
        valores.put(BaseDeDatos.Vehiculo.Columnas.UUID, vehiculo.getmID().toString());
        valores.put(BaseDeDatos.Vehiculo.Columnas.MODELO, vehiculo.getModelo());
        valores.put(BaseDeDatos.Vehiculo.Columnas.VERSION, vehiculo.getVersion());
        valores.put(BaseDeDatos.Vehiculo.Columnas.TRANSMISION, vehiculo.getTransmision());
        valores.put(BaseDeDatos.Vehiculo.Columnas.ESTADO, vehiculo.getEstado());
        valores.put(BaseDeDatos.Vehiculo.Columnas.AÑO, vehiculo.getAño());
        return valores;
    }

    //Insert
    public void AgregarVehiculo(Vehiculo vehiculo){
        ContentValues valores = ObtenerContentValues(vehiculo);
        mBaseDeDatos.insert(BaseDeDatos.Vehiculo.NOMBRE, null, valores);
    }

    //Delete
    public void EliminarVehiculo(Vehiculo vehiculo){
        String VehiculoUUID = vehiculo.getmID().toString();
        mBaseDeDatos.delete(BaseDeDatos.Vehiculo.NOMBRE, BaseDeDatos.Vehiculo.Columnas.UUID + " = ?", new String[] {VehiculoUUID});
    }

    //Update
    public void ActualizarVehiculo(Vehiculo vehiculo){
        String VehiculoUUID = vehiculo.getmID().toString();
        ContentValues valores = ObtenerContentValues(vehiculo);
        mBaseDeDatos.update(BaseDeDatos.Vehiculo.NOMBRE, valores, BaseDeDatos.Vehiculo.Columnas.UUID + " = ?", new String[] {VehiculoUUID});
    }

    //Cursor para obtener los datos del Vehiculo
    private CursorWrapper queryVehiculos(String whereClause, String[] whereArgs){
        Cursor cursor = mBaseDeDatos.query(
                BaseDeDatos.Vehiculo.NOMBRE,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new CursorWrapperVehiculos(cursor);
    }

    //Funcion para obtener la lista de vehiculos
    public List<Vehiculo> ObtenerListaVehiculos(){
        List<Vehiculo> vehiculos = new ArrayList<>();
        CursorWrapperVehiculos cursor = (CursorWrapperVehiculos) queryVehiculos(null, null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                vehiculos.add(cursor.obtenerVehiculo());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return vehiculos;
    }

    //Funcion para obtener un vehiculo en especifico
    public Vehiculo ObtenerVehiculo(UUID id){
        CursorWrapperVehiculos cursor = (CursorWrapperVehiculos) queryVehiculos(
                BaseDeDatos.Vehiculo.Columnas.UUID + " = ?",
                new String[] {id.toString()}
        );

        try{
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.obtenerVehiculo();
        }
        finally {
            cursor.close();
        }
    }

    //Obtener direccion de la fotografia del vehiculo
    public File getPhotoFile(Vehiculo vehiculo) {
        File filesDir = Environment.getExternalStorageDirectory();
        return new File(filesDir, vehiculo.getPhotoFileName());
    }

}
