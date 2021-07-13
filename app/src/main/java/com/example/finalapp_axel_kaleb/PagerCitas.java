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

public class PagerCitas extends AppCompatActivity {

    private static final String CITAS_ID = "citasID";
    private static final String TRAMITAZO2_ID = "tramitazo2ID";
    private ViewPager mViewPager;                   //View Pager
    private List<Cita> mCitas;                      //Lista de citas
    private UUID citaID;                            //ID de la cita clickeada
    private UUID tramiteID;                         //ID del tramite

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.citas_pager);
        tramiteID = (UUID) getIntent().getSerializableExtra(TRAMITAZO2_ID);
        mCitas = AlmacenamientoCitas.ObtenerAlmacenamientoCitas(this).ObtenerListaCitas(tramiteID);         //Se carga lista de citas de ese tramite
        mViewPager = findViewById(R.id.pager_de_citas);
        citaID = (UUID) getIntent().getSerializableExtra(CITAS_ID);                                         //Agarra el ID de la cita seleccionada
        FragmentManager fragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                Cita cita = mCitas.get(position);
                //Retorno newInstance de FragmentoCita
                return FragmentoCita.newInstance(cita.getmID());
            }

            @Override
            public int getCount() {
                return mCitas.size();
            }
        });

        for (int i = 0; i < mCitas.size(); i++){
            if(mCitas.get(i).getmID().equals(citaID)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent newIntent (Context packageContext, UUID citaiD, UUID tramiteID){
        Intent intent = new Intent(packageContext, PagerCitas.class);
        intent.putExtra(CITAS_ID, citaiD);
        intent.putExtra(TRAMITAZO2_ID, tramiteID);
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

