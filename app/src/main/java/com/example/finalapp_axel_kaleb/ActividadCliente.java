package com.example.finalapp_axel_kaleb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.UUID;

public class ActividadCliente extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String ID_DEL_TRAMITE = "id_del_tramite";      //Etiqueta para pasar por medio del Intent el ID del tramite creado/seleccionado
    private static final String isNUEVO = "isnuevo";                    //Etiqueta para pasar por medio del Intent el booleano que indica si se trata de un tramite creado o seleccionado

    //ELEMENTOS DEL XML:
    private EditText mETNombre, mETApellidoPaterno, mETApellidoMaterno, mETCelular, mETActividadLaboral, mETComprobanteIngresos, mETEnganche;
    private Spinner mSpinnerHistorialCrediticio;
    private TextView mTVClienteEtiqueta, mTVHistorialCrediticio;
    private Button mBotonVolver, mBotonFecha, mBotonSiguiente, mBotonEditarInfo, mBotonTerminarEdicion;
    ////////////////////

    private Tramite mTramite;                                           //Almacena el tramite
    private Cliente mCliente;                                           //Almacena el cliente relacionado al tramite
    private boolean mIsNuevo;                                           //Almacena el booleano de control
    private UUID mTramiteID, mClienteID;                                //Almacena los ID del tramite y cliente respectivamente
    private String mFecha;                                              //Almacena temporalmente la fecha obtenida del DatePicker antes de guardar el dato en la Base de Datos


    //onCreate:
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.captura_info_cliente);                   //Se indica la view de la Activity

        //Wiring up:
        mETNombre = findViewById(R.id.cliente_nombre);
        mETApellidoPaterno = findViewById(R.id.cliente_apellido_paterno);
        mETApellidoMaterno = findViewById(R.id.cliente_apellido_materno);
        mETCelular = findViewById(R.id.cliente_celular);
        mETActividadLaboral = findViewById(R.id.cliente_actividad_laboral);
        mETComprobanteIngresos = findViewById(R.id.cliente_comprobante_ingresos);
        mETEnganche = findViewById(R.id.cliente_enganche);
        mSpinnerHistorialCrediticio = findViewById(R.id.spinner_historial_crediticio);
        mTVClienteEtiqueta = findViewById(R.id.cliente_etiqueta);
        mTVHistorialCrediticio = findViewById(R.id.cliente_historial_crediticio);
        mBotonVolver = findViewById(R.id.boton_volver);
        mBotonFecha = findViewById(R.id.cliente_fecha_contacto);
        mBotonSiguiente = findViewById(R.id.boton_siguiente);
        mBotonEditarInfo = findViewById(R.id.boton_editar_info_cliente);
        mBotonTerminarEdicion = findViewById(R.id.boton_terminar_edicion);
        ////////////

        mTramiteID = (UUID) getIntent().getSerializableExtra(ID_DEL_TRAMITE);                                //Se obtiene el ID del tramite proveniente del Intent
        mTramite = AlmacenamientoTramites.ObtenerAlmacenamientoTramites(this).ObtenerTramite(mTramiteID);    //Busca el tramite en la Base de Datos por medio del ID proporcionado por el Intent
        mIsNuevo = (boolean) getIntent().getSerializableExtra(isNUEVO);                                      //Se obtiene el booleano de control proveniente del Intent

        //Si se trata de un nuevo tramite entonces...
        if(mIsNuevo == true){
            mCliente = new Cliente();                                                               //Se crea un nuevo cliente
            AlmacenamientoClientes.ObtenerAlmacenamientoClientes(this).AgregarCliente(mCliente);    //Se guarda el cliente en la BD
            mTramite.setmIDCliente(mCliente.getmID().toString());                                   //Se le asigna al tramite creado en la MainActivity la FK de este nuevo cliente

            //Deshabilita todas las opciones de visualizacion
            mBotonEditarInfo.setVisibility(View.INVISIBLE);
            mBotonVolver.setVisibility(View.INVISIBLE);
            /////////////////////////////////////////////////

        }
        //Si se trata de un tramite ya existente entonces...
        else{
            mClienteID = UUID.fromString(mTramite.getmIDCliente());                                             //Se saca el ID del cliente relacionado al tramite
            mCliente = AlmacenamientoClientes.ObtenerAlmacenamientoClientes(this).ObtenerCliente(mClienteID);   //Busca el cliente en la Base de Datos por medio de su ID

            //Deshabilita todas las opciones de edicion
            mTVClienteEtiqueta.setVisibility(View.GONE);
            mETNombre.setEnabled(false);
            mETApellidoPaterno.setEnabled(false);
            mETApellidoMaterno.setEnabled(false);
            mETCelular.setEnabled(false);
            mETActividadLaboral.setEnabled(false);
            mETComprobanteIngresos.setEnabled(false);
            mETEnganche.setEnabled(false);
            mSpinnerHistorialCrediticio.setEnabled(false);
            mBotonFecha.setEnabled(false);
            ///////////////////////////////////////////

        }

        //Define los valores por default a mostrar en los diferentes objetos del XML
        mETNombre.setText(mCliente.getNombre());
        mETApellidoPaterno.setText(mCliente.getApellido_Paterno());
        mETApellidoMaterno.setText(mCliente.getApellido_Materno());
        mETCelular.setText(mCliente.getCelular());
        mETActividadLaboral.setText(mCliente.getActividad_Laboral());
        mETComprobanteIngresos.setText(mCliente.getComprobante_Ingresos());
        mETEnganche.setText(mCliente.getEnganche());
        mTVHistorialCrediticio.setText(mCliente.getHistorial_Crediticio());
        if(mCliente.getFecha_Contacto() != null){
            mBotonFecha.setText(mCliente.getFecha_Contacto());
        }
        ////////////////////////////////////////////////////////////////////////////

        //Actualizacion de la info del Edit Text del Nombre
        mETNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCliente.setNombre(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ///////////////////////////////////////////////////

        //Actualizacion de la info del Edit Text del Apellido Paterno
        mETApellidoPaterno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCliente.setApellido_Paterno(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /////////////////////////////////////////////////////////////

        //Actualizacion de la info del Edit Text del Apellido materno
        mETApellidoMaterno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCliente.setApellido_Materno(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /////////////////////////////////////////////////////////////

        //Actualizacion de la info del Edit Text del Celular
        mETCelular.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCliente.setCelular(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ////////////////////////////////////////////////////

        //Actualizacion de la info del Edit Text de la Actividad laboral
        mETActividadLaboral.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCliente.setActividad_Laboral(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ////////////////////////////////////////////////////////////////

        //Actualizacion de la info del Edit Text del Comprobante de Ingresos
        mETComprobanteIngresos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCliente.setComprobante_Ingresos(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ////////////////////////////////////////////////////////////////////

        //Actualizacion de la info del Edit Text del Enganche
        mETEnganche.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCliente.setEnganche(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /////////////////////////////////////////////////////

        //Actualizacion de la info del Spinner del Historial Crediticio
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.combo_historial_crediticio,android.R.layout.simple_spinner_item);
        mSpinnerHistorialCrediticio.setAdapter(adapter1);
        mSpinnerHistorialCrediticio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    mTVHistorialCrediticio.setText(parent.getItemAtPosition(position).toString());
                    mCliente.setHistorial_Crediticio(mTVHistorialCrediticio.getText().toString());
                }
                if(position == 0){
                    if(mCliente.getHistorial_Crediticio() != null){
                        mTVHistorialCrediticio.setText(mCliente.getHistorial_Crediticio());
                    }
                    else{
                        mTVHistorialCrediticio.setText(parent.getItemAtPosition(position).toString());
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ///////////////////////////////////////////////////////////////

        //Actualizacion de la info del boton de la Fecha de Contacto
        mBotonFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new FragmentoDatePicker();                      //Se crea un Dialog DatePicker
                datePicker.show(getSupportFragmentManager(), "date picker");           //Manda a mostrar el DatePicker creado
            }
        });
        ////////////////////////////////////////////////////////////

        //Al presionar el boton de editar...
        mBotonEditarInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Habilita todas las opciones de edicion
                mTVClienteEtiqueta.setVisibility(View.VISIBLE);
                mETNombre.setEnabled(true);
                mETApellidoPaterno.setEnabled(true);
                mETApellidoMaterno.setEnabled(true);
                mETCelular.setEnabled(true);
                mETActividadLaboral.setEnabled(true);
                mETComprobanteIngresos.setEnabled(true);
                mETEnganche.setEnabled(true);
                mSpinnerHistorialCrediticio.setEnabled(true);
                mBotonFecha.setEnabled(true);
                mBotonEditarInfo.setVisibility(View.GONE);
                mBotonTerminarEdicion.setVisibility(View.VISIBLE);
                mBotonSiguiente.setVisibility(View.GONE);
                mBotonVolver.setVisibility(View.GONE);
            }
        });
        ////////////////////////////////////

        //Al presionar el boton de terminar edicion...
        mBotonTerminarEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Deshabilita todas las opciones de edicion
                mTVClienteEtiqueta.setVisibility(View.GONE);
                mETNombre.setEnabled(false);
                mETApellidoPaterno.setEnabled(false);
                mETApellidoMaterno.setEnabled(false);
                mETCelular.setEnabled(false);
                mETActividadLaboral.setEnabled(false);
                mETComprobanteIngresos.setEnabled(false);
                mETEnganche.setEnabled(false);
                mSpinnerHistorialCrediticio.setEnabled(false);
                mBotonFecha.setEnabled(false);
                mBotonTerminarEdicion.setVisibility(View.GONE);
                mBotonEditarInfo.setVisibility(View.VISIBLE);
                mBotonSiguiente.setVisibility(View.VISIBLE);
                mBotonVolver.setVisibility(View.VISIBLE);
            }
        });
        //////////////////////////////////////////////

        //Al presionar el boton de siguiente...
        mBotonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Se crea un Intent para mandar a llamar a ActividadTramite, lugar donde se visualizará/introducirá el
                * resto de los datos del tramite*/
                Intent intent = ActividadTramite.newIntentTramite(mBotonSiguiente.getContext(), mTramite.getmID(), mIsNuevo);
                startActivity(intent);
            }
        });
        ///////////////////////////////////////

        //Al presionar el boton de volver...
        mBotonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(mBotonVolver.getContext(), ActividadListaTramites.class);
                startActivity(intent);
            }
        });
        ////////////////////////////////////
    }


    //onPause:
    @Override
    protected void onPause() {
        super.onPause();
        AlmacenamientoClientes.ObtenerAlmacenamientoClientes(this).ActualizarCliente(mCliente);     //Se actualiza el registro de la Base de Datos del cliente
        AlmacenamientoTramites.ObtenerAlmacenamientoTramites(this).ActualizarTramite(mTramite);     //Se actualiza el registro de la Base de Datos del tramite
        /*Es neceesrio actualizar el tramite debido a que si se está creando un nuevo Tramite
        * tambien se estará creano un nuevo cliente, por lo que se afectará la FK de cliente
        * en la tabla tramite. Si no se acutualizara el Tramite no se relacionaría la información
        * del cliente.*/
    }


    //onDateSet (Respuesta del DatePicker):
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();                                        //Se crea un Calendario para almacenar los datos aventados por el DatePicker
        //El año, mes y dia se guardan en las variables proporcionadas como parametros
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        mFecha = DateFormat.getDateInstance().format(c.getTime());                  //Se guarda en mFecha la fecha en formato dia/mes/año
        mBotonFecha.setText("Fecha de contacto: " + mFecha);                        //Se muestra la fecha en el boton mBotonFecha
        mCliente.setFecha_Contacto(mBotonFecha.getText().toString());               //Se guarda la nueva fecha en la base de datos a traves del texto del boton
    }


    //onCreateOptionsMenu:
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Si se trata de un tramite ya existente entonces...
        if(mIsNuevo == false){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_tramite, menu);        //Se infla el menu con el boton para eliminar el tramite
        }
        /*Si se trata de un tramite nuevo no se muestra el boton de eliminar
        * ya que no tiene mucho sentido*/
        return true;
    }


    //onOptionsItemSelected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            //Si se presiona el item de "Eliminar el tramite" entonces...
            case R.id.eliminar_tramite:
                Tramite tramite = mTramite;                                                             //Rescata el tramite en "tramite"
                Cliente cliente = mCliente;                                                             //Rescata el cliente que previamente relacionamos al tramite en "cliente"
                AlmacenamientoTramites.ObtenerAlmacenamientoTramites(this).EliminarTramite(tramite);    //Se elimina el tramite de la Base de Datos
                AlmacenamientoClientes.ObtenerAlmacenamientoClientes(this).EliminarCliente(cliente);    //Se elimina el cliente de la base de Datos
                /*Se manda a llamar un Intent para abrir la Lista de los tramites, esto ya que
                * unicamente es posible eliminar tramites cuando se accede a ellos por medio de
                * la lista, por lo que siempre deberá regresar a esta Activity*/
                Intent intent = new Intent(this, ActividadListaTramites.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /*Intent de ActividadCliente, sus parametros son:
    * tramiteID: Contiene el ID del tramite que fue tapeado en la lista o en su defecto creado en la MainActivity
    * isNuevo: Booleano que tendrá un valor de TRUE si se trata de un tramite nuevo (se llama desde MainActivity)
    *   o FALSE si se trata de un tramite ya existente (se llama desde FragmentoListaTramite)*/
    public static Intent newIntent (Context packageContext, UUID tramiteID, boolean isNuevo){
        Intent intent = new Intent(packageContext, ActividadCliente.class);
        intent.putExtra(ID_DEL_TRAMITE, tramiteID);
        intent.putExtra(isNUEVO, isNuevo);
        return intent;
    }

}