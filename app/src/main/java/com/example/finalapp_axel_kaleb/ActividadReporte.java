package com.example.finalapp_axel_kaleb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class ActividadReporte extends AppCompatActivity {

    private final static String ID_DEL_TRAMITE_REPORTE = "id_del_tramite_reporte";      //Etiqueta para pasar por medio del Intent el ID del tramite seleccionado

    //ELEMENTOS DEL XML:
    private TextView mInfoCliente, mCelular, mActividadLaboral, mFechaContacto, mHistorialCrediticio, mComprobanteIngresos, mEnganche;
    private TextView mPerfil, mEtapa, mFinanciamiento, mActividad;
    private TextView mModelo, mVersion, mTransmision, mEstado, mAño;
    private UUID mTramiteID;
    private Tramite mTramite;
    private Cliente mCliente;
    private Vehiculo mVehiculo;
    ////////////////////

    //onCreate:
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reporte);                         //Se indica la view de la Activity

        //Wiring up:
        mInfoCliente = findViewById(R.id.reporte_nombre);
        mCelular = findViewById(R.id.reporte_celular);
        mActividadLaboral = findViewById(R.id.reporte_actividad_laboral);
        mFechaContacto = findViewById(R.id.reporte_fecha_contacto);
        mHistorialCrediticio = findViewById(R.id.reporte_historial_crediticio);
        mComprobanteIngresos = findViewById(R.id.reporte_comprobante_ingresos);
        mEnganche = findViewById(R.id.reporte_enganche);
        mPerfil = findViewById(R.id.reporte_perfil);
        mEtapa = findViewById(R.id.reporte_etapa);
        mFinanciamiento = findViewById(R.id.reporte_financiamiento);
        mActividad = findViewById(R.id.reporte_actividad_vehiculo);
        mModelo = findViewById(R.id.reporte_modelo);
        mVersion = findViewById(R.id.reporte_version);
        mTransmision = findViewById(R.id.reporte_transmision);
        mEstado = findViewById(R.id.reporte_estado);
        mAño = findViewById(R.id.reporte_año);
        ////////////

        mTramiteID = (UUID) getIntent().getSerializableExtra(ID_DEL_TRAMITE_REPORTE);                                                               //Se obtiene el ID del tramite proveniente del Intent
        mTramite = AlmacenamientoTramites.ObtenerAlmacenamientoTramites(this).ObtenerTramite(mTramiteID);                                           //Busca el tramite en la Base de Datos por medio del ID proporcionado por el Intent
        mCliente = AlmacenamientoClientes.ObtenerAlmacenamientoClientes(this).ObtenerCliente(UUID.fromString(mTramite.getmIDCliente()));            //Busca el cliente relacionado al tramite en la Base de Datos por medio de la FK

        //Si se tiene un vehiculo relacionado al tramite entonces...
        if(mTramite.getmIDVehiculo() != null){
            mVehiculo = AlmacenamientoVehiculos.ObtenerAlmacenamientoVehiculos(this).ObtenerVehiculo(UUID.fromString(mTramite.getmIDVehiculo()));   //Busca el vehiculo relacionado al tramite en la Base de Datos por medio de la FK
        }
        else{
            mVehiculo = null;
        }

        //Datos del cliente
        if(mCliente.getNombre() != null){
            //NOMBRE, AP_PATERNO,
            if(mCliente.getApellido_Paterno() != null){
                //NOMBRE, AP_PATERNO, AP_MATERNO
                if(mCliente.getApellido_Materno()!= null){
                    mInfoCliente.setText(mCliente.getNombre() + " " + mCliente.getApellido_Paterno() + " " + mCliente.getApellido_Materno());
                }
                //NOMBRE, AP_PATERNO, null
                else{
                    mInfoCliente.setText(mCliente.getNombre() + " " + mCliente.getApellido_Paterno());
                }
            }
            //NOMBRE, null
            else{
                //NOMBRE, null, AP_MATERNO
                if(mCliente.getApellido_Materno()!= null){
                    mInfoCliente.setText(mCliente.getNombre() + " " + mCliente.getApellido_Materno());
                }
                //NOMBRE, null, null
                else{
                    mInfoCliente.setText(mCliente.getNombre());
                }
            }
        }
        //null,
        else{
            //null, AP_PATERNO,
            if(mCliente.getApellido_Paterno() != null){
                //null, AP_PATERNO, AP_MATERNO
                if(mCliente.getApellido_Materno()!= null){
                    mInfoCliente.setText(mCliente.getApellido_Paterno() + " " + mCliente.getApellido_Materno());
                }
                //null, AP_PATERNO, null
                else{
                    mInfoCliente.setText(mCliente.getApellido_Paterno());
                }
            }
            //null, null
            else{
                //null, null, AP_MATERNO
                if(mCliente.getApellido_Materno()!= null){
                    mInfoCliente.setText(mCliente.getApellido_Materno());
                }
                //null, null, null
                else{
                    mInfoCliente.setText("Sin definir");
                }
            }
        }
        ///////////////////

        //Celular
        if(mCliente.getCelular() != null){
            mCelular.setText(mCliente.getCelular());
        }
        else{
            mCelular.setText("Sin definir");
        }
        /////////

        //Actividad laboral
        if(mCliente.getActividad_Laboral() != null){
            mActividadLaboral.setText(mCliente.getActividad_Laboral());
        }
        else{
            mActividadLaboral.setText("Sin definir");
        }
        ///////////////////

        //Fecha de contacto
        if(mCliente.getFecha_Contacto() != null){
            mFechaContacto.setText(mCliente.getFecha_Contacto());
        }
        else{
            mFechaContacto.setText("Sin definir");
        }
        ///////////////////

        //Historial crediticio
        if(mCliente.getHistorial_Crediticio() != null){
            mHistorialCrediticio.setText(mCliente.getHistorial_Crediticio());
        }
        else{
            mHistorialCrediticio.setText("Sin definir");
        }
        //////////////////////

        //Comprobante de ingresos
        if(mCliente.getComprobante_Ingresos() != null){
            mComprobanteIngresos.setText(mCliente.getComprobante_Ingresos());
        }
        else{
            mComprobanteIngresos.setText("Sin definir");
        }
        /////////////////////////

        //Enganche
        if(mCliente.getEnganche() != null){
            mEnganche.setText(mCliente.getEnganche());
        }
        else{
            mEnganche.setText("Sin definir");
        }
        //////////

        //Perfil
        if(mTramite.getPerfil_Vehiculo() != null){
            mPerfil.setText(mTramite.getPerfil_Vehiculo());
        }
        else{
            mPerfil.setText("Sin definir");
        }
        ////////

        //Etapa
        if(mTramite.getEtapa() != null){
            mEtapa.setText(mTramite.getEtapa());
        }
        else{
            mEtapa.setText("Sin definir");
        }
        ///////

        //Financiamiento
        if(mTramite.getFinanciamiento() != null){
            mFinanciamiento.setText(mTramite.getFinanciamiento());
        }
        else{
            mFinanciamiento.setText("Sin definir");
        }
        ////////////////

        //Actividad para el vehiculo
        if(mTramite.getActividad() != null){
            mActividad.setText(mTramite.getActividad());
        }
        else{
            mActividad.setText("Sin definir");
        }
        ////////////////////////////

        //Si si se tiene un vehiculo relacionado al tramite entonces...
        if(mVehiculo != null){
            //Modelo
            if(mVehiculo.getModelo() != null){
                mModelo.setText(mVehiculo.getModelo());
            }
            else{
                mModelo.setText("Sin definir");
            }
            ////////

            //Version
            if(mVehiculo.getVersion() != null){
                mVersion.setText(mVehiculo.getVersion());
            }
            else{
                mVersion.setText("Sin definir");
            }
            /////////

            //Transmision
            if(mVehiculo.getTransmision() != null){
                mTransmision.setText(mVehiculo.getTransmision());
            }
            else{
                mTransmision.setText("Sin definir");
            }
            /////////////

            //Estado
            if(mVehiculo.getEstado() != null){
                mEstado.setText(mVehiculo.getEstado());
            }
            else{
                mEstado.setText("Sin definir");
            }
            ////////

            //Año
            if(mVehiculo.getAño() != null){
                mAño.setText(mVehiculo.getAño());
            }
            else{
                mAño.setText("Sin definir");
            }
            /////

        }
        //Si no se tiene un vehiculo relacionado al tramite entonces...
        else{
            //Se pone "Sin definir" en todos los campos relacionados a la informacion del vehiculo
            mModelo.setText("Sin definir");
            mVersion.setText("Sin definir");
            mTransmision.setText("Sin definir");
            mEstado.setText("Sin definir");
            mAño.setText("Sin definir");
        }

    }


    /*Intent de ActividadReporte, sus parametros son:
     * tramiteID: Contiene el ID del tramite que fue tapeado en la lista
     *  para mostrar su reporte*/
    public static Intent newIntent (Context packageContext, UUID tramiteID){
        Intent intent = new Intent(packageContext, ActividadReporte.class);
        intent.putExtra(ID_DEL_TRAMITE_REPORTE, tramiteID);
        return intent;
    }

}
