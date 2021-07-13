package com.example.finalapp_axel_kaleb;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

public class CursorWrapperClientes extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CursorWrapperClientes(Cursor cursor) {
        super(cursor);
    }

    public Cliente obtenerCliente(){
        //Se lee la informacion de la Tabla Cliente de la Base de Datos
        String uuidString = getString(getColumnIndex(BaseDeDatos.Cliente.Columnas.UUID));
        String nombre = getString(getColumnIndex(BaseDeDatos.Cliente.Columnas.NOMBRES));
        String apellido_paterno = getString(getColumnIndex(BaseDeDatos.Cliente.Columnas.APELLIDO_PATERNO));
        String apellido_materno = getString(getColumnIndex(BaseDeDatos.Cliente.Columnas.APELLIDO_MATERNO));
        String celular = getString(getColumnIndex(BaseDeDatos.Cliente.Columnas.CELULAR));
        String actividad_laboral = getString(getColumnIndex(BaseDeDatos.Cliente.Columnas.ACTIVIDAD_LABORAL));
        String historial_crediticio = getString(getColumnIndex(BaseDeDatos.Cliente.Columnas.HISTORIAL_CREDITICIO));
        String comprobante_ingresos = getString(getColumnIndex(BaseDeDatos.Cliente.Columnas.COMPROBANTE_INGRESOS));
        String enganche = getString(getColumnIndex(BaseDeDatos.Cliente.Columnas.ENGANCHE));
        String fecha_contacto = getString(getColumnIndex(BaseDeDatos.Cliente.Columnas.FECHA_CONTACTO));

        //Se asigna la informacion recopilada de la Base de Datos a un objeto tipo Cliente
        Cliente cliente = new Cliente(UUID.fromString(uuidString));
        cliente.setNombre(nombre);
        cliente.setApellido_Paterno(apellido_paterno);
        cliente.setApellido_Materno(apellido_materno);
        cliente.setCelular(celular);
        cliente.setActividad_Laboral(actividad_laboral);
        cliente.setHistorial_Crediticio(historial_crediticio);
        cliente.setComprobante_Ingresos(comprobante_ingresos);
        cliente.setEnganche(enganche);
        cliente.setFecha_Contacto(fecha_contacto);

        return cliente;
    }
}
