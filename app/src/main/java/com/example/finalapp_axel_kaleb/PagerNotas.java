package com.example.finalapp_axel_kaleb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;

public class PagerNotas extends AppCompatActivity {

    private static final String NOTAS_ID = "notasID";
    private static final String TRAMITAZO_ID = "tramitazoID";
    private ViewPager mViewPager;           //View Pager
    private List<Nota> mNotas;              //Lista de notas
    UUID notaID;                            //ID de la nota clickeada
    UUID tramiteID;                         //ID del tramite

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notas_pager);
        tramiteID = (UUID) getIntent().getSerializableExtra(TRAMITAZO_ID);
        mNotas = AlmacenamientoNotas.ObtenerAlmacenamientoNotas(this).ObtenerListaNotas(tramiteID);         //Se carga lista de notas de ese tramite
        mViewPager = findViewById(R.id.pager_de_notas);
        notaID = (UUID) getIntent().getSerializableExtra(NOTAS_ID);                                       //Agarra el ID de la nota seleccionada
        FragmentManager fragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                Nota nota = mNotas.get(position);
                //Retorno newInstance de FragmentoCita
                return FragmentoNota.newInstance(nota.getmID());
            }

            @Override
            public int getCount() {
                return mNotas.size();
            }
        });

        for (int i = 0; i < mNotas.size(); i++){
            if(mNotas.get(i).getmID().equals(notaID)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent newIntent (Context packageContext, UUID notaiD, UUID tramiteID){
        Intent intent = new Intent(packageContext, PagerNotas.class);
        intent.putExtra(NOTAS_ID, notaiD);
        intent.putExtra(TRAMITAZO_ID, tramiteID);
        return intent;
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStack();
        }
        else{
            super.onBackPressed();
        }
    }
}
