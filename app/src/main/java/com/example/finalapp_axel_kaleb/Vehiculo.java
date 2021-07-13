package com.example.finalapp_axel_kaleb;

import java.util.UUID;

public class Vehiculo {
    public UUID mID;
    public String Modelo;
    public String Version;
    public String Transmision;
    public String Estado;
    public String Año;

    //Constructor
    public Vehiculo(UUID id){
        mID = id;
    }

    public Vehiculo(){
        this(UUID.randomUUID());    //Se genera un ID unico
    }

    public UUID getmID() {
        return mID;
    }

    public void setmID(UUID mID) {
        this.mID = mID;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getTransmision() {
        return Transmision;
    }

    public void setTransmision(String transmision) {
        Transmision = transmision;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getAño() {
        return Año;
    }

    public void setAño(String año) {
        Año = año;
    }

    public String getPhotoFileName(){
        return "IMG_" + getmID().toString() + ".jpg";
    }
}
