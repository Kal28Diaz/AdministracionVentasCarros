package com.example.finalapp_axel_kaleb;

import java.util.Date;
import java.util.UUID;

public class Cliente {
    public UUID mID;
    public String Nombre;
    public String Apellido_Paterno;
    public String Apellido_Materno;
    public String Celular;
    public String Actividad_Laboral;
    public String Historial_Crediticio;
    public String Comprobante_Ingresos;
    public String Enganche;
    public String Fecha_Contacto;

    //Constructor
    public Cliente(UUID id){
        mID = id;
    }

    public Cliente(){
        this(UUID.randomUUID());    //Se genera un UUID unico
    }

    public UUID getmID() {
        return mID;
    }

    public void setmID(UUID mID) {
        this.mID = mID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido_Paterno() {
        return Apellido_Paterno;
    }

    public void setApellido_Paterno(String apellido_Paterno) {
        Apellido_Paterno = apellido_Paterno;
    }

    public String getApellido_Materno() {
        return Apellido_Materno;
    }

    public void setApellido_Materno(String apellido_Materno) {
        Apellido_Materno = apellido_Materno;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }

    public String getActividad_Laboral() {
        return Actividad_Laboral;
    }

    public void setActividad_Laboral(String actividad_Laboral) {
        Actividad_Laboral = actividad_Laboral;
    }

    public String getHistorial_Crediticio() {
        return Historial_Crediticio;
    }

    public void setHistorial_Crediticio(String historial_Crediticio) {
        Historial_Crediticio = historial_Crediticio;
    }

    public String getComprobante_Ingresos() {
        return Comprobante_Ingresos;
    }

    public void setComprobante_Ingresos(String comprobante_Ingresos) {
        Comprobante_Ingresos = comprobante_Ingresos;
    }

    public String getEnganche() {
        return Enganche;
    }

    public void setEnganche(String enganche) {
        Enganche = enganche;
    }

    public String getFecha_Contacto() {
        return Fecha_Contacto;
    }

    public void setFecha_Contacto(String fecha_Contacto) {
        Fecha_Contacto = fecha_Contacto;
    }
}
