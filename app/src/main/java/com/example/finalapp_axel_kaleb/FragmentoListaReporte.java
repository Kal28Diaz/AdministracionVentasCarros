package com.example.finalapp_axel_kaleb;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.UUID;

public class FragmentoListaReporte extends Fragment {
    private RecyclerView mRecyclerView;
    private TextView mTextView;
    private FragmentoListaReporte.TramitesAdapter mAdaptador;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmento_lista_reporte, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view_reportes);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTextView = view.findViewById(R.id.alerta_reportes);
        updateUI();
        return view;
    }

    private void updateUI() {
        //Singleton
        AlmacenamientoTramites almacenamientoTramites = AlmacenamientoTramites.ObtenerAlmacenamientoTramites(getActivity());

        //Lista de Tramites
        List<Tramite> tramites = almacenamientoTramites.ObtenerListaTramites();

        if(mAdaptador == null){
            mAdaptador = new FragmentoListaReporte.TramitesAdapter(tramites);    //El adaptador recibe la lista de tramites
            mRecyclerView.setAdapter(mAdaptador);  //Indicamos el adaptador a la RecyclerView
        }
        else {
            mAdaptador.setmTramites(tramites);
            mAdaptador.notifyDataSetChanged();
        }
    }

    //View Holder
    private class TramitesHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTVNombre, mTVFecha, mTVEtapa;

        private Tramite mTramite;
        private UUID mClienteID;
        private Cliente mCliente;

        @Override
        public void onClick(View v) {
            Intent intent = ActividadReporte.newIntent(getActivity(), mTramite.getmID());
            startActivity(intent);
        }

        //Constructor (Se le implementa la Recycler View)
        public TramitesHolder(LayoutInflater inflater, ViewGroup parent) {
            super (inflater.inflate(R.layout.item_lista_tramite, parent, false));
            itemView.setOnClickListener(this);      //Implementa los taps en los items de la Recycler View

            //Wiring Up
            mTVNombre = itemView.findViewById(R.id.item_nombre_prospecto);
            mTVFecha = itemView.findViewById(R.id.item_fecha_prospecto);
            mTVEtapa = itemView.findViewById(R.id.item_etapa_tramite);
        }

        public void bind(Tramite tramite){
            mTramite = tramite;
            mClienteID = UUID.fromString(mTramite.getmIDCliente());
            mCliente = AlmacenamientoClientes.ObtenerAlmacenamientoClientes(getActivity()).ObtenerCliente(mClienteID);
            //NOMBRE,
            if(mCliente.getNombre() != null){
                //NOMBRE, AP_PATERNO,
                if(mCliente.getApellido_Paterno() != null){
                    //NOMBRE, AP_PATERNO, AP_MATERNO
                    if(mCliente.getApellido_Materno()!= null){
                        mTVNombre.setText(mCliente.getNombre() + " " + mCliente.getApellido_Paterno() + " " + mCliente.getApellido_Materno());
                    }
                    //NOMBRE, AP_PATERNO, null
                    else{
                        mTVNombre.setText(mCliente.getNombre() + " " + mCliente.getApellido_Paterno());
                    }
                }
                //NOMBRE, null
                else{
                    //NOMBRE, null, AP_MATERNO
                    if(mCliente.getApellido_Materno()!= null){
                        mTVNombre.setText(mCliente.getNombre() + " " + mCliente.getApellido_Materno());
                    }
                    //NOMBRE, null, null
                    else{
                        mTVNombre.setText(mCliente.getNombre());
                    }
                }
            }
            //null,
            else{
                //null, AP_PATERNO,
                if(mCliente.getApellido_Paterno() != null){
                    //null, AP_PATERNO, AP_MATERNO
                    if(mCliente.getApellido_Materno()!= null){
                        mTVNombre.setText(mCliente.getApellido_Paterno() + " " + mCliente.getApellido_Materno());
                    }
                    //null, AP_PATERNO, null
                    else{
                        mTVNombre.setText(mCliente.getApellido_Paterno());
                    }
                }
                //null, null
                else{
                    //null, null, AP_MATERNO
                    if(mCliente.getApellido_Materno()!= null){
                        mTVNombre.setText(mCliente.getApellido_Materno());
                    }
                    //null, null, null
                    else{
                        mTVNombre.setText(" ");
                    }
                }
            }

            mTVFecha.setText(mCliente.getFecha_Contacto());
            mTVEtapa.setText(mTramite.getEtapa());
        }
    }

    //Adaptador
    private class TramitesAdapter extends RecyclerView.Adapter<FragmentoListaReporte.TramitesHolder> {
        private List<Tramite> mTramites;

        //Constructor
        public TramitesAdapter(List<Tramite> tramites){
            mTramites = tramites;
        }

        @NonNull
        @Override
        public FragmentoListaReporte.TramitesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new FragmentoListaReporte.TramitesHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull FragmentoListaReporte.TramitesHolder holder, int position) {
            Tramite tramite = mTramites.get(position);
            holder.bind(tramite);
        }

        @Override
        public int getItemCount() {
            return mTramites.size();
        }

        public void setmTramites(List<Tramite> tramites){
            mTramites = tramites;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
        NoTramites();
    }

    private void NoTramites() {
        AlmacenamientoTramites almacenamientoTramites = AlmacenamientoTramites.ObtenerAlmacenamientoTramites(getActivity());
        int Contador = almacenamientoTramites.ObtenerListaTramites().size();

        if(Contador<1){
            mTextView.setText("No se encontraron tramites. Crea uno en la pantalla principal por medio del boton de Capturar nuevo prospecto");
        }
        else{
            mTextView.setText("Seleccione el tramite correspondiente al reporte a generar");
        }
    }
}
