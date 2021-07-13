package com.example.finalapp_axel_kaleb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ActividadTramite extends AppCompatActivity {

    private static final String ID_DEL_TRAMITE_T = "id_del_tramitet";  //Etiqueta para pasar por medio del Intent el ID del tramite creado/seleccionado
    private static final String isNUEVO_T = "isnuevot";                //Etiqueta para pasar por medio del Intent el booleano que indica si se trata de un tramite creado o seleccionado

    //ELEMENTOS DEL XML:
    private EditText mETPerfil;
    private Spinner mSpinnerActividad, mSpinnerEtapa, mSpinnerFinanciamiento, mSpinnerVehiculo;
    private TextView mTVEtiqueta, mTVactividad, mTVEtapa, mTVFinanciamiento, mTVNombreVehiculo, mTVAñoVehiculo;
    private Button mBotonVolver, mBotonNotas, mBotonCitas, mBotonEditarInfo, mBotonTerminarEdicion, mBotonFinalizar;
    private ImageView mImagenVehiculo, mImagenNotas, mImagenCitas;
    ////////////////////

    private Tramite mTramite;                                          //Almacena el tramite
    private Vehiculo mVehiculo;                                        //Almacena el vehiculo relacionado al tramite
    private boolean mIsNuevo;                                          //Almacena el booleano de control
    private UUID mTramiteID, mVehiculoID;                              //Almacena los ID del tramite y vehiculo respectivamente
    private File mFileFoto;                                            //Almacena la direccion de la foto del vehiculo
    private ArrayList<String> mListaVehiculosSpinner;                  //Almacena una lista de Strings correspondientes a cada registro de la tabla Vehiculo para ser mostrado en un spinner
    private List<Vehiculo> mListaVehiculos;                            //Almacena la lista de vehiculos almacenados en la Base de Datos

    //onCreate:
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.captura_info_tramite);                 //Se indica la view de la Activity

        //Wiring up:
        mETPerfil = findViewById(R.id.tramite_perfil);
        mSpinnerActividad = findViewById(R.id.spinner_actividad);
        mSpinnerEtapa = findViewById(R.id.spinner_etapa);
        mSpinnerFinanciamiento = findViewById(R.id.spinner_financiamiento);
        mSpinnerVehiculo = findViewById(R.id.spinner_vehiculo);
        mTVEtiqueta = findViewById(R.id.tramite_etiqueta);
        mTVactividad = findViewById(R.id.tramite_actividad);
        mTVEtapa = findViewById(R.id.tramite_etapa);
        mTVFinanciamiento = findViewById(R.id.tramite_financiamiento);
        mTVNombreVehiculo = findViewById(R.id.tramite_nombre_vehiculo);
        mTVAñoVehiculo = findViewById(R.id.tramite_año_vehiculo);
        mBotonVolver = findViewById(R.id.boton_volver_2);
        mBotonNotas = findViewById(R.id.boton_notas);
        mBotonCitas = findViewById(R.id.boton_citas);
        mBotonEditarInfo = findViewById(R.id.boton_editar_info_tramite);
        mBotonTerminarEdicion = findViewById(R.id.boton_terminar_edicion_2);
        mBotonFinalizar = findViewById(R.id.boton_finalizar);
        mImagenVehiculo = findViewById(R.id.tramite_imagen_vehiculo);
        mImagenNotas = findViewById(R.id.tramite_imagen_notas);
        mImagenCitas = findViewById(R.id.tramite_imagen_citas);
        ////////////

        mTramiteID = (UUID) getIntent().getSerializableExtra(ID_DEL_TRAMITE_T);                             //Se obtiene el ID del tramite proveniente del Intent
        mTramite = AlmacenamientoTramites.ObtenerAlmacenamientoTramites(this).ObtenerTramite(mTramiteID);   //Busca el tramite en la Base de Datos por medio del ID proporcionado por el Intent
        mIsNuevo = (boolean) getIntent().getSerializableExtra(isNUEVO_T);                                   //Se obtiene el booleano de control proveniente del Intent


        //Si se trata de un nuevo tramite entonces...
        if(mIsNuevo == true){
            //Deshabilita todas las opciones de visualizacion
            mImagenNotas.setVisibility(View.INVISIBLE);
            mBotonNotas.setVisibility(View.INVISIBLE);
            mImagenCitas.setVisibility(View.INVISIBLE);
            mBotonCitas.setVisibility(View.INVISIBLE);
            mBotonEditarInfo.setVisibility(View.GONE);
            mBotonVolver.setVisibility(View.INVISIBLE);
            /////////////////////////////////////////////////
        }
        //Si se trata de un tramite ya existente entonces...
        else{
            //Deshabilita todas las opciones de edicion
            mTVEtiqueta.setVisibility(View.GONE);
            mSpinnerActividad.setEnabled(false);
            mETPerfil.setEnabled(false);
            mSpinnerEtapa.setEnabled(false);
            mSpinnerFinanciamiento.setEnabled(false);
            mSpinnerVehiculo.setEnabled(false);
            mBotonFinalizar.setVisibility(View.GONE);
            ///////////////////////////////////////////
        }

        //Define los valores por default a mostrar en los diferentes objetos del XML
        mTVactividad.setText(mTramite.getActividad());
        mETPerfil.setText(mTramite.getPerfil_Vehiculo());
        mTVEtapa.setText(mTramite.getEtapa());
        mTVFinanciamiento.setText(mTramite.getFinanciamiento());
        //Si existe un vehiculo relacionado al tramite entonces...
        if(mTramite.getmIDVehiculo() != null) {
            mVehiculoID = UUID.fromString(mTramite.getmIDVehiculo());                                              //Se obtiene el ID de dicho vehiculo por medio de la FK del registro del tramite de la Base de Datos
            mVehiculo = AlmacenamientoVehiculos.ObtenerAlmacenamientoVehiculos(this).ObtenerVehiculo(mVehiculoID); //Se busca el vehiculo en la Base de Datos
            mTVNombreVehiculo.setText(mVehiculo.getModelo());
            mTVAñoVehiculo.setText(mVehiculo.getAño());
            mFileFoto = AlmacenamientoVehiculos.ObtenerAlmacenamientoVehiculos(this).getPhotoFile(mVehiculo);      //Se obtiene la direccion de la foto correspondiente al vehiculo
            Bitmap bitmap = BitmapFactory.decodeFile(mFileFoto.toString());                                        //Decodifica la foto
            //Si existe una foto la va a mostrar al momento de cargar el vehiculo, en caso contrario no hara nada.
            if (bitmap != null) {
                //Manda a llamar la funcion para ajustar la imagen al tamaño de la Image View.
                bitmap = ajustar_foto(bitmap);
                mImagenVehiculo.setImageBitmap(bitmap);
            }
        }
        ////////////////////////////////////////////////////////////////////////////

        //Actualizacion de la info del Edit Text del Perfil del vehiculo
        mETPerfil.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTramite.setPerfil_Vehiculo(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ////////////////////////////////////////////////////////////////

        //Actualizacion de la info del Spinner de Actividad
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.combo_actividad,android.R.layout.simple_spinner_item);
        mSpinnerActividad.setAdapter(adapter1);
        mSpinnerActividad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    mTVactividad.setText(parent.getItemAtPosition(position).toString());
                    mTramite.setActividad(mTVactividad.getText().toString());
                }
                if(position == 0){
                    if(mTramite.getActividad() != null){
                        mTVactividad.setText(mTramite.getActividad());
                    }
                    else{
                        mTVactividad.setText(parent.getItemAtPosition(position).toString());
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ///////////////////////////////////////////////////

        //Actualizacion de la info del Spinner de la Etapa
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.combo_etapa,android.R.layout.simple_spinner_item);
        mSpinnerEtapa.setAdapter(adapter2);
        mSpinnerEtapa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    mTVEtapa.setText(parent.getItemAtPosition(position).toString());
                    mTramite.setEtapa(mTVEtapa.getText().toString());
                }
                if(position == 0){
                    if(mTramite.getEtapa() != null){
                        mTVEtapa.setText(mTramite.getEtapa());
                    }
                    else{
                        mTVEtapa.setText(parent.getItemAtPosition(position).toString());
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //////////////////////////////////////////////////

        //Actualizacion de la info del Spinner del tipo de Financiamiento
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.combo_financiamiento,android.R.layout.simple_spinner_item);
        mSpinnerFinanciamiento.setAdapter(adapter3);
        mSpinnerFinanciamiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    mTVFinanciamiento.setText(parent.getItemAtPosition(position).toString());
                    mTramite.setFinanciamiento(mTVFinanciamiento.getText().toString());
                }
                if(position == 0){
                    if(mTramite.getFinanciamiento() != null){
                        mTVFinanciamiento.setText(mTramite.getFinanciamiento());
                    }
                    else{
                        mTVFinanciamiento.setText(parent.getItemAtPosition(position).toString());
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /////////////////////////////////////////////////////////////////


        //Actualizacion de la info del Spinner del Vehiculo
        obtenerListaVehiculos();                                                                                                                        //Se obtiene la lista de vehiculos en forma de String para asignarle al adaptador del spinner
        ArrayAdapter<CharSequence> adaptador_vehiculo = new ArrayAdapter(this,android.R.layout.simple_spinner_item, mListaVehiculosSpinner);
        mSpinnerVehiculo.setAdapter(adaptador_vehiculo);
        mSpinnerVehiculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Si la posicion es mayor a 0 (se seleccionó un vehiculo) entonces...
                if(position > 0){
                    int posicionVehiculo= position-1;
                    mVehiculoID = mListaVehiculos.get(posicionVehiculo).mID;                                                                            //Obtiene el ID del vehiculo por medio de la posicion
                    mVehiculo = AlmacenamientoVehiculos.ObtenerAlmacenamientoVehiculos(mSpinnerVehiculo.getContext()).ObtenerVehiculo(mVehiculoID);     //Obtiene el vehiculo de la Base de Datos
                    mFileFoto = AlmacenamientoVehiculos.ObtenerAlmacenamientoVehiculos(mSpinnerVehiculo.getContext()).getPhotoFile(mVehiculo);          //Obtiene el archivo de la foto del vehiculo
                    mTVNombreVehiculo.setText(mVehiculo.getModelo());
                    mTVAñoVehiculo.setText(mVehiculo.getAño());
                    Bitmap bitmap = BitmapFactory.decodeFile(mFileFoto.toString());                                                                     //Decodifica la foto
                    //Si existe una foto la va a mostrar al momento de cargar el vehiculo, en caso contrario no hara nada.
                    if (bitmap != null) {
                        //Manda a llamar la funcion para ajustar la imagen al tamaño de la Image View
                        bitmap = ajustar_foto(bitmap);
                        mImagenVehiculo.setImageBitmap(bitmap);
                    }
                    mTramite.setmIDVehiculo(mVehiculoID.toString());
                }
                if(position == 0){
                    if(mTramite.getmIDVehiculo() != null){
                        mTVNombreVehiculo.setText(mVehiculo.getModelo());
                        mTVAñoVehiculo.setText(mVehiculo.getAño());
                        Bitmap bitmap = BitmapFactory.decodeFile(mFileFoto.toString()); //Decodifica la foto
                        //Si existe una foto la va a mostrar al momento de cargar el vehiculo. En caso contrario no hara nada
                        if (bitmap != null) {
                            //Manda a llamar la funcion para ajustar la imagen al tamaño de la Image View
                            bitmap = ajustar_foto(bitmap);
                            mImagenVehiculo.setImageBitmap(bitmap);
                        }
                        mTramite.setmIDVehiculo(mVehiculoID.toString());
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ///////////////////////////////////////////////////

        //Al presionar el boton de notas
        mBotonNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Se crea un Intent para mandar a llamar a ActividadListaNotas, lugar donde se visualizarán las notas
                 * correspondientes a este tramite*/
                Intent intentazo = ActividadListaNotas.newIntentNota(mBotonNotas.getContext(), mTramite.getmID());
                startActivity(intentazo);
            }
        });
        ////////////////////////////////

        //Al presionar el boton de citas
        mBotonCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Se crea un Intent para mandar a llamar a ActividadListaNotas, lugar donde se visualizarán las citas
                 * correspondientes a este tramite*/
                Intent intentazo = ActividadListaCitas.newIntentCita(mBotonNotas.getContext(), mTramite.getmID());
                startActivity(intentazo);
            }
        });
        ////////////////////////////////

        //Al presionar el boton de Editar Info del tramite
        mBotonEditarInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Habilita todas las opciones de edicion
                mBotonVolver.setVisibility(View.GONE);
                mTVEtiqueta.setVisibility(View.VISIBLE);
                mSpinnerActividad.setEnabled(true);
                mETPerfil.setEnabled(true);
                mSpinnerEtapa.setEnabled(true);
                mSpinnerFinanciamiento.setEnabled(true);
                mSpinnerVehiculo.setEnabled(true);
                mImagenNotas.setVisibility(View.INVISIBLE);
                mBotonNotas.setVisibility(View.INVISIBLE);
                mImagenCitas.setVisibility(View.INVISIBLE);
                mBotonCitas.setVisibility(View.INVISIBLE);
                mBotonEditarInfo.setVisibility(View.GONE);
                mBotonTerminarEdicion.setVisibility(View.VISIBLE);
                ////////////////////////////////////////
            }
        });
        //////////////////////////////////////////////////

        //Al presionar el boton de Terminar edicion
        mBotonTerminarEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Deshabilita todas las opciones de edicion
                mBotonVolver.setVisibility(View.VISIBLE);
                mTVEtiqueta.setVisibility(View.GONE);
                mSpinnerActividad.setEnabled(false);
                mETPerfil.setEnabled(false);
                mSpinnerEtapa.setEnabled(false);
                mSpinnerFinanciamiento.setEnabled(false);
                mSpinnerVehiculo.setEnabled(false);
                mImagenNotas.setVisibility(View.VISIBLE);
                mBotonNotas.setVisibility(View.VISIBLE);
                mImagenCitas.setVisibility(View.VISIBLE);
                mBotonCitas.setVisibility(View.VISIBLE);
                mBotonEditarInfo.setVisibility(View.GONE);
                mBotonEditarInfo.setVisibility(View.VISIBLE);
                mBotonTerminarEdicion.setVisibility(View.GONE);
                ///////////////////////////////////////////
            }
        });
        ///////////////////////////////////////////

        //Al presionar el boton de volver
        mBotonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ActividadCliente.newIntent(mBotonVolver.getContext(), mTramite.getmID(), false);
                startActivity(intent);
            }
        });
        /////////////////////////////////

        //Al presionar el boton de finalizar
        mBotonFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mBotonFinalizar.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        ////////////////////////////////////
    }

    //Funcion para obtener la lista de vehiculos:
    private void obtenerListaVehiculos() {
        mListaVehiculos = AlmacenamientoVehiculos.ObtenerAlmacenamientoVehiculos(this).ObtenerListaVehiculos();     //Se obtiene la lista de los vehiculos almacenados en la Base de Datos
        mListaVehiculosSpinner = new ArrayList<>();
        mListaVehiculosSpinner.add(" ");
        for(int i=0; i<mListaVehiculos.size(); i++){
            mListaVehiculosSpinner.add(mListaVehiculos.get(i).getModelo() + ", " + mListaVehiculos.get(i).getAño() + ", " + mListaVehiculos.get(i).getVersion());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        AlmacenamientoTramites.ObtenerAlmacenamientoTramites(this).ActualizarTramite(mTramite);
    }

    //Funcion para ajustar el tamaño de la foto al tamaño de la ImageView
    private Bitmap ajustar_foto(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = 720;
        int newHeight = 500;

        // calculamos el escalado de la imagen destino
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // para poder manipular la imagen
        // debemos crear una matriz

        Matrix matrix = new Matrix();
        // resize the Bitmap
        matrix.postScale(scaleWidth, scaleHeight);

        // volvemos a crear la imagen con los nuevos valores
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                width, height, matrix, true);
        bitmap = resizedBitmap;
        return bitmap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(mIsNuevo == false){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_tramite, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.eliminar_tramite:
                Tramite tramite = mTramite;
                Cliente cliente = AlmacenamientoClientes.ObtenerAlmacenamientoClientes(this).ObtenerCliente(UUID.fromString(mTramite.getmIDCliente()));
                AlmacenamientoTramites.ObtenerAlmacenamientoTramites(this).EliminarTramite(tramite);
                AlmacenamientoClientes.ObtenerAlmacenamientoClientes(this).EliminarCliente(cliente);
                Intent intent = new Intent(this, ActividadListaTramites.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Intent newIntentTramite (Context packageContext, UUID tramiteID, boolean isNuevo){
        Intent intent = new Intent(packageContext, ActividadTramite.class);
        intent.putExtra(ID_DEL_TRAMITE_T, tramiteID);
        intent.putExtra(isNUEVO_T, isNuevo);
        return intent;
    }

}
