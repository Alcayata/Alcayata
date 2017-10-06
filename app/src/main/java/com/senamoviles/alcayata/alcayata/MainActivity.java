package com.senamoviles.alcayata.alcayata;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.senamoviles.alcayata.alcayata.MainFragments.FloresFragment;
import com.senamoviles.alcayata.alcayata.MainFragments.InfoFragment;
import com.senamoviles.alcayata.alcayata.MainFragments.ModeloFragment;
import com.ss.bottomnavigation.BottomNavigation;
import com.ss.bottomnavigation.events.OnSelectedItemChangeListener;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    public static String opcion = "San Juan Evangelista";


    @BindView(R.id.spinner)
    MaterialSpinner spinner;
    @BindView(R.id.frame_fragment_containers)
    FrameLayout frameFragmentContainers;
    @BindView(R.id.botton_nav)
    BottomNavigation bottonNav;
    Fragment fragment = null;
    private FragmentTransaction transaction;

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bottonNav.setDefaultItem(2);
        spinner.setItems("San Juan Evangelista","El Crucifijo","Virgen de los Dolores","El Señor del Huerto");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Toast.makeText(MainActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
                opcion = item.toString();

            }
        });

        bottonNav.setOnSelectedItemChangeListener(new OnSelectedItemChangeListener() {
            @Override
            public void onSelectedItemChanged(int i) {
                switch (i){
                    case R.id.item_3d:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers,new ModeloFragment());
                        fragment = new ModeloFragment();
                        break;
                    case R.id.item_paso:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers,new InfoFragment());
                        fragment = new InfoFragment();
                        break;
                    case R.id.item_flower:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers,new FloresFragment());
                        fragment = new FloresFragment();
                        break;
                    case R.id.item_volume:
                        // TODO 1: decidir que agregar
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers,new ModeloFragment());
                        fragment = new ModeloFragment();
                        break;
                    case R.id.item_citial:
                        // TODO 2: decidir que agregar
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers,new InfoFragment());
                        fragment = new InfoFragment();
                        break;

                }
                transaction.commit();
            }
        });


    }
}
