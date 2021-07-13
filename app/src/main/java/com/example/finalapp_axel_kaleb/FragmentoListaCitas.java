package com.example.finalapp_axel_kaleb;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.UUID;

public class FragmentoListaCitas extends Fragment {

    private static final String ARG_TRAMITE_ID_2 = "arg_tramite_id";
    private RecyclerView mRecyclerView;
    private FragmentoListaCitas.CitasAdapter mAdaptador;
    private TextView mtextView;
    private Button mBotonVolver;
    private UUID mIDTramite;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIDTramite = (UUID) getArguments().getSerializable(ARG_TRAMITE_ID_2);     //Guarda el ID del tramite
        setHasOptionsMenu(true);                                                //Habilita opciones de menu
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_lista_citas,container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view_citas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mtextView = view.findViewById(R.id.alerta_citas);
        mBotonVolver = view.findViewById(R.id.boton_volver_5);

        mBotonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ActividadTramite.newIntentTramite(getActivity(), mIDTramite, false);
                startActivity(intent);
            }
        });

        updateUI();
        return view;
    }

    private void updateUI(){
        //Singleton
        AlmacenamientoCitas almacenamientoCitas = AlmacenamientoCitas.ObtenerAlmacenamientoCitas(getActivity());

        //Lista de citas
        List<Cita> citas = almacenamientoCitas.ObtenerListaCitas(mIDTramite);

        if(mAdaptador == null){
            mAdaptador = new FragmentoListaCitas.CitasAdapter(citas);    //El adaptador recibe la lista de citas
            mRecyclerView.setAdapter(mAdaptador);  //Indicamos el adaptador a la RecyclerView
        }
        else {
            mAdaptador.setmCitas(citas);
            mAdaptador.notifyDataSetChanged();
        }
    }

    //View Holder
    private class CitasHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTitulo, mFecha, mHora;
        private Cita mCita;

        @Override
        public void onClick(View v) {
            Intent intent = PagerCitas.newIntent(getActivity(), mCita.getmID(), mIDTramite);
            startActivity(intent);
        }

        //Constructor (Se le implementa la Recycler View)
        public CitasHolder(LayoutInflater inflater, ViewGroup parent) {
            super (inflater.inflate(R.layout.item_lista_citas, parent, false));
            itemView.setOnClickListener(this);      //Implementa los taps en los items de la Recycler View

            //Wiring Up
            mTitulo = itemView.findViewById(R.id.item_titulo_cita);
            mFecha = itemView.findViewById(R.id.item_fecha_cita);
            mHora = itemView.findViewById(R.id.item_hora_cita);
        }

        public void bind(Cita cita){
            mCita = cita;
            mTitulo.setText(cita.getTitulo());
            mFecha.setText(cita.getFecha());
            mHora.setText(cita.getHora());
        }
    }

    //Adaptador
    private class CitasAdapter extends RecyclerView.Adapter<FragmentoListaCitas.CitasHolder> {
        private List<Cita> mCitas;

        //Constructor
        public CitasAdapter(List<Cita> citas){
            mCitas = citas;
        }

        @NonNull
        @Override
        public FragmentoListaCitas.CitasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new FragmentoListaCitas.CitasHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull FragmentoListaCitas.CitasHolder holder, int position) {
            Cita cita = mCitas.get(position);
            holder.bind(cita);
        }

        @Override
        public int getItemCount() {
            return mCitas.size();
        }

        public void setmCitas(List<Cita> citas){
            mCitas = citas;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
        NoCitas();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_listas, menu); //Se infla el menu
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.agregar_vehiculo:
                Cita cita = new Cita();
                cita.setmIDTramite(mIDTramite.toString());
                AlmacenamientoCitas.ObtenerAlmacenamientoCitas(getActivity()).AgregarCita(cita);
                Intent intent = PagerCitas.newIntent(getActivity(), cita.getmID(), mIDTramite);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void NoCitas(){
        AlmacenamientoCitas almacenamientoCitas = AlmacenamientoCitas.ObtenerAlmacenamientoCitas(getActivity());
        int Contador = almacenamientoCitas.ObtenerListaCitas(mIDTramite).size();
        if(Contador<1){
            mtextView.setVisibility(View.VISIBLE);
        }
        else{
            mtextView.setVisibility(View.GONE);
        }
    }

    public static FragmentoListaCitas newInstance (UUID TramiteID){
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_TRAMITE_ID_2, TramiteID);   //Recibe el ID del Tramite
        //Creamos fragmento que envie el fragmento
        FragmentoListaCitas fragment = new FragmentoListaCitas();
        fragment.setArguments(arguments);
        return fragment;
    }
}
