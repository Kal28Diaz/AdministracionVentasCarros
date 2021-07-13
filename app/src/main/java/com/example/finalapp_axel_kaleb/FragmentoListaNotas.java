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

public class FragmentoListaNotas extends Fragment {

    private static final String ARG_TRAMITE_ID = "arg_tramite_id";
    private RecyclerView mRecyclerView;
    private FragmentoListaNotas.NotasAdapter mAdaptador;
    private TextView mtextView;
    private Button mBotonVolver;
    private UUID mIDTramite;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIDTramite = (UUID) getArguments().getSerializable(ARG_TRAMITE_ID);     //Guarda el ID del tramite
        setHasOptionsMenu(true);                    //Habilita opciones de menu
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragmento_lista_notas,container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view_notas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mtextView = view.findViewById(R.id.alerta_notas);
        mBotonVolver = view.findViewById(R.id.boton_volver_3);

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
        AlmacenamientoNotas almacenamientoNotas = AlmacenamientoNotas.ObtenerAlmacenamientoNotas(getActivity());

        //Lista de notas
        List<Nota> notas = almacenamientoNotas.ObtenerListaNotas(mIDTramite);

        if(mAdaptador == null){
            mAdaptador = new FragmentoListaNotas.NotasAdapter(notas);    //El adaptador recibe la lista de notas
            mRecyclerView.setAdapter(mAdaptador);  //Indicamos el adaptador a la RecyclerView
        }
        else {
            mAdaptador.setmNotas(notas);
            mAdaptador.notifyDataSetChanged();
        }
    }

    //View Holder
    private class NotasHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTitulo;
        private Nota mNota;

        @Override
        public void onClick(View v) {
            Intent intent = PagerNotas.newIntent(getActivity(), mNota.getmID(), mIDTramite);
            startActivity(intent);
        }

        //Constructor (Se le implementa la Recycler View)
        public NotasHolder(LayoutInflater inflater, ViewGroup parent) {
            super (inflater.inflate(R.layout.item_lista_notas, parent, false));
            itemView.setOnClickListener(this);      //Implementa los taps en los items de la Recycler View

            //Wiring Up
            mTitulo = itemView.findViewById(R.id.item_titulo_nota);
        }

        public void bind(Nota nota){
            mNota = nota;
            mTitulo.setText(nota.Titulo);
        }
    }

    //Adaptador
    private class NotasAdapter extends RecyclerView.Adapter<FragmentoListaNotas.NotasHolder> {
        private List<Nota> mNotas;

        //Constructor
        public NotasAdapter(List<Nota> notas){
            mNotas = notas;
        }

        @NonNull
        @Override
        public FragmentoListaNotas.NotasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new FragmentoListaNotas.NotasHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull FragmentoListaNotas.NotasHolder holder, int position) {
            Nota nota = mNotas.get(position);
            holder.bind(nota);
        }

        @Override
        public int getItemCount() {
            return mNotas.size();
        }

        public void setmNotas(List<Nota> notas){
            mNotas = notas;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
        NoNotas();
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
                Nota nota = new Nota();
                nota.setmIDTramite(mIDTramite.toString());
                AlmacenamientoNotas.ObtenerAlmacenamientoNotas(getActivity()).AgregarNota(nota);
                Intent intent = PagerNotas.newIntent(getActivity(), nota.getmID(), mIDTramite);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void NoNotas(){
        AlmacenamientoNotas almacenamientoNotas = AlmacenamientoNotas.ObtenerAlmacenamientoNotas(getActivity());
        int Contador = almacenamientoNotas.ObtenerListaNotas(mIDTramite).size();
        if(Contador<1){
            mtextView.setVisibility(View.VISIBLE);
        }
        else{
            mtextView.setVisibility(View.GONE);
        }
    }

    public static FragmentoListaNotas newInstance (UUID TramiteID){
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_TRAMITE_ID, TramiteID);   //Recibe el ID del Tramite
        //Creamos fragmento que envie el fragmento
        FragmentoListaNotas fragment = new FragmentoListaNotas();
        fragment.setArguments(arguments);
        return fragment;
    }
}
