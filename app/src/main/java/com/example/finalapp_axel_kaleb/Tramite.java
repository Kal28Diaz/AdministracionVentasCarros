package com.example.finalapp_axel_kaleb;

import java.util.UUID;

public class Tramite {
    public UUID mID;
    public String Actividad;
    public String Perfil_Vehiculo;
    public String Etapa;
    public String Financiamiento;
    public String mIDCliente;
    public String mIDVehiculo;

    //Constructor
    public Tramite(UUID id){
        mID = id;
    }

    public Tramite(){
        this(UUID.randomUUID());    //Se genera un UUID unico
    }

    public UUID getmID() {
        return mID;
    }

    public void setmID(UUID mID) {
        this.mID = mID;
    }

    public String getActividad() {
        return Actividad;
    }

    public void setActividad(String actividad) {
        Actividad = actividad;
    }

    public String getPerfil_Vehiculo() {
        return Perfil_Vehiculo;
    }

    public void setPerfil_Vehiculo(String perfil_Vehiculo) {
        Perfil_Vehiculo = perfil_Vehiculo;
    }

    public String getEtapa() {
        return Etapa;
    }

    public void setEtapa(String etapa) {
        Etapa = etapa;
    }

    public String getFinanciamiento() {
        return Financiamiento;
    }

    public void setFinanciamiento(String financiamiento) {
        Financiamiento = financiamiento;
    }

    public String getmIDCliente() {
        return mIDCliente;
    }

    public void setmIDCliente(String mIDCliente) {
        this.mIDCliente = mIDCliente;
    }

    public String getmIDVehiculo() {
        return mIDVehiculo;
    }

    public void setmIDVehiculo(String mIDVehiculo) {
        this.mIDVehiculo = mIDVehiculo;
    }
}
