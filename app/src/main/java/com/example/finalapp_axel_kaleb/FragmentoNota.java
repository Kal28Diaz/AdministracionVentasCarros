package com.example.finalapp_axel_kaleb;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.UUID;

public class FragmentoNota extends Fragment {

    private static final String PARAMETRO_NOTA = "parametronotaid";

    private Nota mNota;
    private EditText mTitulo, mCuerpo;
    private Button mBotonVolver;
    private UUID mNotaID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotaID = (UUID) getArguments().getSerializable(PARAMETRO_NOTA);
        mNota = AlmacenamientoNotas.ObtenerAlmacenamientoNotas(getActivity()).ObtenerNota(mNotaID);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.nota, container, false);

        //Wiring Up
        mTitulo = v.findViewById(R.id.titulo_nota);
        mCuerpo = v.findViewById(R.id.cuerpo_nota);
        mBotonVolver = v.findViewById(R.id.boton_volver_4);

        //Default
        mTitulo.setText(mNota.getTitulo());
        mCuerpo.setText(mNota.getCuerpo());

        //Actualizacion de la info del Edit Text del titulo de la nota
        mTitulo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNota.setTitulo(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Actualizacion de la info del Edit Text del cuerpo de la nota
        mCuerpo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNota.setCuerpo(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBotonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ActividadListaNotas.newIntentNota(getActivity(), UUID.fromString(mNota.getmIDTramite()));
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        AlmacenamientoNotas.ObtenerAlmacenamientoNotas(getActivity()).ActualizarNota(mNota);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragmento_nota, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.eliminar_nota:
                Nota nota = mNota;
                AlmacenamientoNotas.ObtenerAlmacenamientoNotas(getActivity()).EliminarNota(nota);
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static FragmentoNota newInstance (UUID notaID){
        Bundle arguments = new Bundle();
        arguments.putSerializable(PARAMETRO_NOTA, notaID);
        FragmentoNota fragmentoNota = new FragmentoNota();
        fragmentoNota.setArguments(arguments);
        return fragmentoNota;
    }
}
