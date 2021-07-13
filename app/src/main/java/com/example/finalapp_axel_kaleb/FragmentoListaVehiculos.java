package com.example.finalapp_axel_kaleb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class FragmentoListaVehiculos extends Fragment {

    private RecyclerView mRecyclerViewVehiculos;
    private VehiculosAdapter mAdaptador;
    private TextView mtextView;

    private File mFileFoto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);                    //Habilita opciones de menu
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmento_lista_vehiculos,container, false);
        mRecyclerViewVehiculos = view.findViewById(R.id.recycler_view_vehiculos);
        mRecyclerViewVehiculos.setLayoutManager(new LinearLayoutManager(getActivity()));
        mtextView = view.findViewById(R.id.alerta_vehiculos);

        updateUI();
        return view;
    }

    private void updateUI(){
        //Singleton
        AlmacenamientoVehiculos almacenamientoVehiculos = AlmacenamientoVehiculos.ObtenerAlmacenamientoVehiculos(getActivity());

        //Lista de vehiculos
        List<Vehiculo> vehiculos = almacenamientoVehiculos.ObtenerListaVehiculos();

        if(mAdaptador == null){
            mAdaptador = new VehiculosAdapter(vehiculos);    //El adaptador recibe la lista de vehiculos
            mRecyclerViewVehiculos.setAdapter(mAdaptador);  //Indicamos el adaptador a la RecyclerView
        }
        else {
            mAdaptador.setmVehiculos(vehiculos);
            mAdaptador.notifyDataSetChanged();
        }
    }

    //View Holder
    private class VehiculosHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView mImageView;
        private TextView mModelo;
        private TextView mAño;
        private Vehiculo mVehiculo;

        @Override
        public void onClick(View v) {
            Intent intent = PagerVehiculos.newIntent(getActivity(), mVehiculo.getmID());
            startActivity(intent);
        }

        //Constructor (Se le implementa la Recycler View)
        public VehiculosHolder(LayoutInflater inflater, ViewGroup parent) {
            super (inflater.inflate(R.layout.item_lista_vehiculos, parent, false));
            itemView.setOnClickListener(this);      //Implementa los taps en los items de la Recycler View

            //Wiring Up
            mImageView = itemView.findViewById(R.id.item_imagen);
            mModelo = itemView.findViewById(R.id.item_modelo);
            mAño = itemView.findViewById(R.id.item_año);
        }

        public void bind(Vehiculo vehiculo){
            mVehiculo = vehiculo;
            mModelo.setText(vehiculo.getModelo());
            mAño.setText(vehiculo.getAño());
            mFileFoto = AlmacenamientoVehiculos.ObtenerAlmacenamientoVehiculos(getActivity()).getPhotoFile(mVehiculo);
            Bitmap bitmap = BitmapFactory.decodeFile(mFileFoto.toString()); //Decodifica la foto
            //Si existe una foto la va a mostrar al momento de cargar el vehiculo. En caso contrario no hara nada
            if(bitmap != null){
                //Manda a llamar la funcion para ajustar la imagen al tamaño de la Image View
                bitmap = ajustar_foto(bitmap);
                mImageView.setImageBitmap(bitmap);
            }
        }
    }

    //Adaptador
    private class VehiculosAdapter extends RecyclerView.Adapter<VehiculosHolder> {
        private List<Vehiculo> mVehiculos;

        //Constructor
        public VehiculosAdapter(List<Vehiculo> vehiculos){
            mVehiculos = vehiculos;
        }

        @NonNull
        @Override
        public VehiculosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new VehiculosHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull VehiculosHolder holder, int position) {
            Vehiculo vehiculo = mVehiculos.get(position);
            holder.bind(vehiculo);
        }

        @Override
        public int getItemCount() {
            return mVehiculos.size();
        }

        public void setmVehiculos(List<Vehiculo> vehiculos){
            mVehiculos = vehiculos;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
        NoVehiculos();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_listas, menu); //Se infla el menu
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.agregar_vehiculo:
                Vehiculo vehiculo = new Vehiculo();
                AlmacenamientoVehiculos.ObtenerAlmacenamientoVehiculos(getActivity()).AgregarVehiculo(vehiculo);
                Intent intent = PagerVehiculos.newIntent(getActivity(), vehiculo.getmID());
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void NoVehiculos(){
        AlmacenamientoVehiculos almacenamientoVehiculos = AlmacenamientoVehiculos.ObtenerAlmacenamientoVehiculos(getActivity());
        int Contador = almacenamientoVehiculos.ObtenerListaVehiculos().size();

        if(Contador<1){
            mtextView.setVisibility(View.VISIBLE);
        }
        else{
            mtextView.setVisibility(View.GONE);
        }
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
}
