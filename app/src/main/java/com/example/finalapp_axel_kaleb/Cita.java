package com.example.finalapp_axel_kaleb;

import java.util.UUID;

public class Cita {
    public UUID mID;
    public String Titulo;
    public String Fecha;
    public String Hora;
    public String mIDTramite;

    //Constructor
    public Cita(UUID id){
        mID = id;
    }

    public Cita(){
        this(UUID.randomUUID());    //Se genera un UUID unico
    }

    public UUID getmID() {
        return mID;
    }

    public void setmID(UUID mID) {
        this.mID = mID;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getmIDTramite() {
        return mIDTramite;
    }

    public void setmIDTramite(String mIDTramite) {
        this.mIDTramite = mIDTramite;
    }
}
