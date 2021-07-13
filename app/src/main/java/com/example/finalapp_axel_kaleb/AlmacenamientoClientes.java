package com.example.finalapp_axel_kaleb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AlmacenamientoClientes {

    private static AlmacenamientoClientes mAlmacenamientoClientes;
    private Context mContexto;
    private SQLiteDatabase mBaseDeDatos;

    //Funcion para crear el almacenamiento de Clientes
    public static AlmacenamientoClientes ObtenerAlmacenamientoClientes(Context context){
        if(mAlmacenamientoClientes == null){
            mAlmacenamientoClientes = new AlmacenamientoClientes(context);
        }
        return mAlmacenamientoClientes;
    }

    //Constructor
    private AlmacenamientoClientes(Context context){
        mContexto = context.getApplicationContext();
        mBaseDeDatos = new ConexionSQLiteHelper(mContexto).getWritableDatabase();   //Se crea la Base de Datos
    }

    //Obtiene los valores a insertar en la Base de Datos en la tabla "Cliente"
    private static ContentValues ObtenerContentValues(Cliente cliente){
        ContentValues valores = new ContentValues();
        valores.put(BaseDeDatos.Cliente.Columnas.UUID, cliente.getmID().toString());
        valores.put(BaseDeDatos.Cliente.Columnas.NOMBRES, cliente.getNombre());
        valores.put(BaseDeDatos.Cliente.Columnas.APELLIDO_PATERNO, cliente.getApellido_Paterno());
        valores.put(BaseDeDatos.Cliente.Columnas.APELLIDO_MATERNO, cliente.getApellido_Materno());
        valores.put(BaseDeDatos.Cliente.Columnas.CELULAR, cliente.getCelular());
        valores.put(BaseDeDatos.Cliente.Columnas.ACTIVIDAD_LABORAL, cliente.getActividad_Laboral());
        valores.put(BaseDeDatos.Cliente.Columnas.HISTORIAL_CREDITICIO, cliente.getHistorial_Crediticio());
        valores.put(BaseDeDatos.Cliente.Columnas.COMPROBANTE_INGRESOS, cliente.getComprobante_Ingresos());
        valores.put(BaseDeDatos.Cliente.Columnas.ENGANCHE, cliente.getEnganche());
        valores.put(BaseDeDatos.Cliente.Columnas.FECHA_CONTACTO, cliente.getFecha_Contacto());
        return valores;
    }

    //Insert
    public void AgregarCliente(Cliente cliente){
        ContentValues valores = ObtenerContentValues(cliente);
        mBaseDeDatos.insert(BaseDeDatos.Cliente.NOMBRE, null, valores);
    }

    //Delete
    public void EliminarCliente(Cliente cliente){
        String ClienteUUID = cliente.getmID().toString();
        mBaseDeDatos.delete(BaseDeDatos.Cliente.NOMBRE, BaseDeDatos.Cliente.Columnas.UUID + " = ?", new String[] {ClienteUUID});
    }

    //Update
    public void ActualizarCliente(Cliente cliente){
        String ClienteUUID = cliente.getmID().toString();
        ContentValues valores = ObtenerContentValues(cliente);
        mBaseDeDatos.update(BaseDeDatos.Cliente.NOMBRE, valores, BaseDeDatos.Cliente.Columnas.UUID + " = ?", new String[] {ClienteUUID});
    }

    //Cursor para obtener los datos del Cliente
    private CursorWrapper queryClientes(String whereClause, String[] whereArgs){
        Cursor cursor = mBaseDeDatos.query(
                BaseDeDatos.Cliente.NOMBRE,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new CursorWrapperClientes(cursor);
    }

    //Funcion para obtener la lista de clientes
    public List<Cliente> ObtenerListaClientes(){
        List<Cliente> clientes = new ArrayList<>();
        CursorWrapperClientes cursor = (CursorWrapperClientes) queryClientes(null, null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                clientes.add(cursor.obtenerCliente());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return clientes;
    }

    //Funcion para obtener un cliente en especifico
    public Cliente ObtenerCliente(UUID id){
        CursorWrapperClientes cursor = (CursorWrapperClientes) queryClientes(
                BaseDeDatos.Cliente.Columnas.UUID + " = ?",
                new String[] {id.toString()}
        );

        try{
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.obtenerCliente();
        }
        finally {
            cursor.close();
        }
    }

}
