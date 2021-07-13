package com.example.finalapp_axel_kaleb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "BaseDeDatos.db";

    //Al llamar al constructor se crea la Base de Datos
    public ConexionSQLiteHelper(Context context){
        super (context,DATABASE_NAME,null,VERSION);
    }

    //Genera las tablas
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create de la Tabla Vehiculo
        db.execSQL("CREATE TABLE " +
                BaseDeDatos.Vehiculo.NOMBRE +
                "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                BaseDeDatos.Vehiculo.Columnas.UUID + ", " +
                BaseDeDatos.Vehiculo.Columnas.MODELO + ", " +
                BaseDeDatos.Vehiculo.Columnas.VERSION + ", " +
                BaseDeDatos.Vehiculo.Columnas.TRANSMISION + ", " +
                BaseDeDatos.Vehiculo.Columnas.ESTADO + ", " +
                BaseDeDatos.Vehiculo.Columnas.AÑO +
                ")"
        );

        //Create de la Tabla Cliente
        db.execSQL("CREATE TABLE " +
                BaseDeDatos.Cliente.NOMBRE +
                "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                BaseDeDatos.Cliente.Columnas.UUID + ", " +
                BaseDeDatos.Cliente.Columnas.NOMBRES + ", " +
                BaseDeDatos.Cliente.Columnas.APELLIDO_PATERNO + ", " +
                BaseDeDatos.Cliente.Columnas.APELLIDO_MATERNO + ", " +
                BaseDeDatos.Cliente.Columnas.CELULAR + ", " +
                BaseDeDatos.Cliente.Columnas.ACTIVIDAD_LABORAL + ", " +
                BaseDeDatos.Cliente.Columnas.HISTORIAL_CREDITICIO + ", " +
                BaseDeDatos.Cliente.Columnas.COMPROBANTE_INGRESOS + ", " +
                BaseDeDatos.Cliente.Columnas.ENGANCHE + ", " +
                BaseDeDatos.Cliente.Columnas.FECHA_CONTACTO +
                ")"
        );

        //Create de la Tabla Tramite
        db.execSQL("CREATE TABLE " +
                BaseDeDatos.Tramite.NOMBRE +
                "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                BaseDeDatos.Tramite.Columnas.UUID + ", " +
                BaseDeDatos.Tramite.Columnas.ACTIVIDAD + ", " +
                BaseDeDatos.Tramite.Columnas.PERFIL_VEHICULO + ", " +
                BaseDeDatos.Tramite.Columnas.ETAPA + ", " +
                BaseDeDatos.Tramite.Columnas.FINANCIAMIENTO + ", " +
                BaseDeDatos.Tramite.Columnas.ID_CLIENTE + ", " +
                BaseDeDatos.Tramite.Columnas.ID_VEHICULO + ", " +
                "FOREIGN KEY (" + BaseDeDatos.Tramite.Columnas.ID_CLIENTE + ") REFERENCES " + BaseDeDatos.Cliente.NOMBRE + "(" + BaseDeDatos.Cliente.Columnas.UUID + "), " +
                "FOREIGN KEY (" + BaseDeDatos.Tramite.Columnas.ID_VEHICULO + ") REFERENCES " + BaseDeDatos.Vehiculo.NOMBRE + "(" + BaseDeDatos.Vehiculo.Columnas.UUID + ")" +
                ")"
        );

        //Create de la Tabla Nota
        db.execSQL("CREATE TABLE " +
                BaseDeDatos.Nota.NOMBRE +
                "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                BaseDeDatos.Nota.Columnas.UUID + ", " +
                BaseDeDatos.Nota.Columnas.TITULO + ", " +
                BaseDeDatos.Nota.Columnas.CUERPO + ", " +
                BaseDeDatos.Nota.Columnas.ID_TRAMITE + ", " +
                "FOREIGN KEY (" + BaseDeDatos.Nota.Columnas.ID_TRAMITE + ") REFERENCES " + BaseDeDatos.Tramite.NOMBRE + "(" + BaseDeDatos.Tramite.Columnas.UUID + ")" +
                ")"
        );

        //Create de la Tabla Cita
        db.execSQL("CREATE TABLE " +
                BaseDeDatos.Cita.NOMBRE +
                "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                BaseDeDatos.Cita.Columnas.UUID + ", " +
                BaseDeDatos.Cita.Columnas.TITULO + ", " +
                BaseDeDatos.Cita.Columnas.FECHA + ", " +
                BaseDeDatos.Cita.Columnas.HORA + ", " +
                BaseDeDatos.Cita.Columnas.ID_TRAMITE + ", " +
                "FOREIGN KEY (" + BaseDeDatos.Cita.Columnas.ID_TRAMITE + ") REFERENCES " + BaseDeDatos.Tramite.NOMBRE + "(" + BaseDeDatos.Tramite.Columnas.UUID + ")" +
                ")"
        );
    }

    //Verifica si ya existe una version de la Base de Datos y se encarga de actualizarla
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
