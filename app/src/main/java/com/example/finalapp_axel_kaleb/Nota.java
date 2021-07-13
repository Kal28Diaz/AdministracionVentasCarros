package com.example.finalapp_axel_kaleb;

import java.util.UUID;

public class Nota {
    public UUID mID;
    public String Titulo;
    public String Cuerpo;
    public String mIDTramite;

    //Constructor
    public Nota(UUID id){
        mID = id;
    }

    public Nota(){
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

    public String getCuerpo() {
        return Cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        Cuerpo = cuerpo;
    }

    public String getmIDTramite() {
        return mIDTramite;
    }

    public void setmIDTramite(String mIDTramite) {
        this.mIDTramite = mIDTramite;
    }
}
