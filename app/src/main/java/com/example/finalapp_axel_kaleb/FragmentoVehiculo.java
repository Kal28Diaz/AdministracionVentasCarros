package com.example.finalapp_axel_kaleb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class FragmentoVehiculo extends Fragment {

    private static final String ARG_VEHICULO_ID = "vehiculo_ID";
    private static final int REQUEST_GALERIA = 1;
    private static final int REQUEST_CAMARA = 2;

    private Vehiculo mVehiculo;
    private ImageView mImageView;
    private Button mBotonImagen;
    private EditText mEditModelo, mEditVersion, mEditAño;
    private Spinner mSpinnerTransmision, mSpinnerEstado;
    private TextView mTVTransmision, mTVEstado;
    private File mFileFoto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID crimeID = (UUID) getArguments().getSerializable(ARG_VEHICULO_ID);
        mVehiculo = AlmacenamientoVehiculos.ObtenerAlmacenamientoVehiculos(getActivity()).ObtenerVehiculo(crimeID); //Busca el vehiculo
        setHasOptionsMenu(true);        //Habilitamos opciones de menu
        mFileFoto = AlmacenamientoVehiculos.ObtenerAlmacenamientoVehiculos(getActivity()).getPhotoFile(mVehiculo);  //Obtiene direccion de la foto
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragmento_vehiculo, container, false);

        //Wiring up
        mImageView = v.findViewById(R.id.imagen_vehiculo);
        mBotonImagen = v.findViewById(R.id.boton_imagen);
        mEditModelo = v.findViewById(R.id.ET_modelo);
        mEditAño = v.findViewById(R.id.ET_año);
        mEditVersion = v.findViewById(R.id.ET_version);
        mSpinnerEstado = v.findViewById(R.id.spinner_estado);
        mSpinnerTransmision = v.findViewById(R.id.spinner_transmision);
        mTVEstado = v.findViewById(R.id.TV_Estado);
        mTVTransmision = v.findViewById(R.id.TV_Transmision);

        //Default
        mEditModelo.setText(mVehiculo.getModelo());
        mEditVersion.setText(mVehiculo.getVersion());
        mEditAño.setText(mVehiculo.getAño());
        if(mVehiculo.getTransmision() != null) {
            mTVTransmision.setVisibility(View.VISIBLE);
            mTVTransmision.setText(mVehiculo.getTransmision());
        }
        if(mVehiculo.getEstado() != null){
            mTVEstado.setVisibility(View.VISIBLE);
            mTVEstado.setText(mVehiculo.getEstado());
        }

        //Actualizacion de la info del Edit Text del modelo
        mEditModelo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mVehiculo.setModelo(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Actualizacion de la info del Edit Text de la version
        mEditVersion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mVehiculo.setVersion(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Actualizacion de la info del Edit Text del año
        mEditAño.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mVehiculo.setAño(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Actualizacion de la info del Spinner del Estado
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.combo_estados,android.R.layout.simple_spinner_item);
        mSpinnerEstado.setAdapter(adapter1);
        mSpinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    mTVEstado.setText(parent.getItemAtPosition(position).toString());
                    mVehiculo.setEstado(mTVEstado.getText().toString());
                }
                if(position==0){
                    if(mVehiculo.getTransmision() != null){
                        mTVEstado.setText(mVehiculo.getEstado());
                    }
                    else{
                        mTVEstado.setText(parent.getItemAtPosition(position).toString());
                    }

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Actualizacion de la info del Spinner de la transmision
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.combo_transmisiones,android.R.layout.simple_spinner_item);
        mSpinnerTransmision.setAdapter(adapter2);
        //mSpinnerTransmision.setSelection(-1);
        mSpinnerTransmision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    mTVTransmision.setText(parent.getItemAtPosition(position).toString());
                    mVehiculo.setTransmision(mTVTransmision.getText().toString());
                }
                if(position == 0){
                    if(mVehiculo.getTransmision() != null){
                        mTVTransmision.setText(mVehiculo.getTransmision());
                    }
                    else{
                        mTVTransmision.setText(parent.getItemAtPosition(position).toString());
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Al tapear el boton de "Agregar imagen"
        mBotonImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarImagen();
            }
        });


        Bitmap bitmap = BitmapFactory.decodeFile(mFileFoto.toString()); //Decodifica la foto
        //Si existe una foto la va a mostrar al momento de cargar el vehiculo. En caso contrario no hara nada
        if(bitmap != null){
            //Manda a llamar la funcion para ajustar la imagen al tamaño de la Image View
            ajustar_foto(bitmap);
        }

        return v;
    }

    public static FragmentoVehiculo newInstance (UUID VehiculoID){
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_VEHICULO_ID, VehiculoID);
        FragmentoVehiculo fragmentoVehiculo = new FragmentoVehiculo();
        fragmentoVehiculo.setArguments(arguments);
        return fragmentoVehiculo;
    }

    @Override
    public void onPause() {
        super.onPause();
        AlmacenamientoVehiculos.ObtenerAlmacenamientoVehiculos(getActivity()).ActualizarVehiculo(mVehiculo);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_fragmento_vehiculos, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.eliminar_vehiculo:
                Vehiculo vehiculo = mVehiculo;
                AlmacenamientoVehiculos.ObtenerAlmacenamientoVehiculos(getActivity()).EliminarVehiculo(vehiculo);

                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void agregarImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"), REQUEST_GALERIA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_GALERIA){
            //Guarda la foto en un URI y la coonvierte a Bitmap
            Bitmap bitmap = null;
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Decodifica la foto
            convertBitmapToFile(bitmap);

            bitmap = BitmapFactory.decodeFile(mFileFoto.toString()); //Decodifica la foto
            ajustar_foto(bitmap);

        }

        if(requestCode == REQUEST_CAMARA){

        }
    }

    public void convertBitmapToFile(Bitmap bitmap) {
        //File filesDir = getAppContext().getFilesDir();
        mFileFoto = AlmacenamientoVehiculos.ObtenerAlmacenamientoVehiculos(getActivity()).getPhotoFile(mVehiculo);

        OutputStream os;
        try {
            os = new FileOutputStream(mFileFoto.toString());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }
    }

    //Funcion para ajustar el tamaño de la foto al tamaño de la ImageView
    private void ajustar_foto(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = 2270;
        int newHeight = 1400;

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
        mImageView.setImageBitmap(bitmap);
    }
}
