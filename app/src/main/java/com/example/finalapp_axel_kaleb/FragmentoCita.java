package com.example.finalapp_axel_kaleb;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.UUID;

public class FragmentoCita extends Fragment {

    private static final String PARAMETRO_CITA = "parametrocitaid";

    private Cita mCita;
    private EditText mTitulo;
    private Button mBotonVolver, mBotonFecha, mBotonHora;
    private UUID mCitaID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCitaID = (UUID) getArguments().getSerializable(PARAMETRO_CITA);
        mCita = AlmacenamientoCitas.ObtenerAlmacenamientoCitas(getActivity()).ObtenerCita(mCitaID);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cita, container, false);

        //Wiring Up
        mTitulo = v.findViewById(R.id.titulo_cita_xd);
        mBotonFecha = v.findViewById(R.id.boton_fecha_cita);
        mBotonHora = v.findViewById(R.id.boton_hora_cita);
        mBotonVolver = v.findViewById(R.id.boton_volver_6);

        //Default
        mTitulo.setText(mCita.getTitulo());
        if(mCita.getFecha() != null){
            mBotonFecha.setText(mCita.getFecha());
        }
        if(mCita.getHora() != null){
            mBotonHora.setText(mCita.getHora());
        }

        //Actualizacion de la info del Edit Text del titulo de la cita
        mTitulo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCita.setTitulo(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Actualizacion de la info del boton de la Fecha de la cita
        mBotonFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario = Calendar.getInstance();
                int yy = calendario.get(Calendar.YEAR);
                int mm = calendario.get(Calendar.MONTH);
                int dd = calendario.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String mFecha = DateFormat.getDateInstance().format(c.getTime());
                        mBotonFecha.setText("Fecha de la cita: " + mFecha);
                        mCita.setFecha(mBotonFecha.getText().toString());
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });

        //Actualizacion de la info del Boton de la Hora de la cita
        mBotonHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario = Calendar.getInstance();
                int hh = calendario.get(Calendar.HOUR_OF_DAY);
                int mm = calendario.get(Calendar.MINUTE);

                TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, hourOfDay);
                        c.set(Calendar.MONTH, minute);
                        if(hourOfDay<10){
                            if(minute<10){
                                mBotonHora.setText("Hora de la cita: 0" + hourOfDay + ":0" + minute);

                            }
                            else{
                                mBotonHora.setText("Hora de la cita: 0" + hourOfDay + ":" + minute);
                            }
                        }
                        else{
                            if(minute<10){
                                mBotonHora.setText("Hora de la cita: " + hourOfDay + ":0" + minute);
                            }
                            else{
                                mBotonHora.setText("Hora de la cita: " + hourOfDay + ":" + minute);
                            }
                        }

                        mCita.setHora(mBotonHora.getText().toString());
                    }
                }, hh, mm, android.text.format.DateFormat.is24HourFormat(getActivity()));
                timePicker.show();
            }
        });

        mBotonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ActividadListaCitas.newIntentCita(getActivity(), UUID.fromString(mCita.getmIDTramite()));
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        AlmacenamientoCitas.ObtenerAlmacenamientoCitas(getActivity()).ActualizarCita(mCita);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragmento_cita, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.eliminar_cita:
                Cita cita = mCita;
                AlmacenamientoCitas.ObtenerAlmacenamientoCitas(getActivity()).EliminarCita(cita);
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static FragmentoCita newInstance (UUID citaID){
        Bundle arguments = new Bundle();
        arguments.putSerializable(PARAMETRO_CITA, citaID);
        FragmentoCita fragmentoCita = new FragmentoCita();
        fragmentoCita.setArguments(arguments);
        return fragmentoCita;
    }
}
