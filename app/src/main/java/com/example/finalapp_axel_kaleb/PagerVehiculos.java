package com.example.finalapp_axel_kaleb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Placeholder;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;

public class PagerVehiculos extends AppCompatActivity {

    private static final String VEHICULO_ID = "vehiculoID";
    private ViewPager mViewPager;               //View Pager
    private List<Vehiculo> mVehiculos;          //Lista de vehiculos
    UUID vehiculoID;                            //ID del vehiculo clickeado

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.vehiculos_pager);
        mVehiculos = AlmacenamientoVehiculos.ObtenerAlmacenamientoVehiculos(this).ObtenerListaVehiculos();  //Se carga lista de vehiculos
        mViewPager = findViewById(R.id.pager_de_vehiculos);
        vehiculoID = (UUID) getIntent().getSerializableExtra(VEHICULO_ID);                                  //Agarra el ID del vehiculo seleccionado
        FragmentManager fragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                Vehiculo vehiculo = mVehiculos.get(position);
                //Retorno newInstance de FragmentoVehiculo
                return FragmentoVehiculo.newInstance(vehiculo.getmID());
            }

            @Override
            public int getCount() {
                return mVehiculos.size();
            }
        });

        for (int i = 0; i < mVehiculos.size(); i++){
            if(mVehiculos.get(i).getmID().equals(vehiculoID)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent newIntent (Context packageContext, UUID vehiculoiD){
        Intent intent = new Intent(packageContext, PagerVehiculos.class);
        intent.putExtra(VEHICULO_ID, vehiculoiD);
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
