package com.senamoviles.alcayata.alcayata;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.senamoviles.alcayata.alcayata.MainFragments.AudioFragment;
import com.senamoviles.alcayata.alcayata.MainFragments.DescargaFragment;
import com.senamoviles.alcayata.alcayata.MainFragments.FloresFragment;
import com.senamoviles.alcayata.alcayata.MainFragments.InfoFragment;
import com.senamoviles.alcayata.alcayata.MainFragments.ModeloFragment;
import com.ss.bottomnavigation.BottomNavigation;
import com.ss.bottomnavigation.events.OnSelectedItemChangeListener;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements BeaconConsumer {

    public static String opcion = "San Juan Evangelista";
    public static final String TAG = "Semana Santa";
    public static String tagfragment = "";


    @BindView(R.id.spinner)
    MaterialSpinner spinner;
    @BindView(R.id.frame_fragment_containers)
    FrameLayout frameFragmentContainers;
    @BindView(R.id.botton_nav)
    BottomNavigation bottonNav;
    Fragment fragment = null;
    Fragment frg = null;
    private FragmentTransaction transaction;
    BeaconManager beaconManager;
    Fragment currentFragment = null;

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        frg = getSupportFragmentManager().findFragmentById(R.id.frame_fragment_containers);

        beaconManager=BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.bind(this);

        bottonNav.setDefaultItem(2);
        spinner.setItems("San Juan Evangelista","El Crucifijo","Virgen de los Dolores","El Señor del Huerto");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Toast.makeText(MainActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
                opcion = item.toString();
                //recargar fragment Info
                currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_fragment_containers);
                recargaFrag(currentFragment);

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
                    case R.id.item_cita:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers,new FloresFragment());
                        fragment = new FloresFragment();
                        break;
                    case R.id.item_audio:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers,new AudioFragment());
                        fragment = new AudioFragment();
                        break;
                    case R.id.item_download:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers,new DescargaFragment());
                        fragment = new DescargaFragment();
                        break;
                }
                transaction.commit();
            }
        });
    }

    public void recargaFrag(Fragment f){
        if(f instanceof InfoFragment){
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_fragment_containers,new InfoFragment());
            fragment = new InfoFragment();
            transaction.commit();
        }else if(f instanceof FloresFragment){
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_fragment_containers,new FloresFragment());
            fragment = new FloresFragment();
            transaction.commit();

        }else if(f instanceof DescargaFragment){
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_fragment_containers,new DescargaFragment());
            fragment = new DescargaFragment();
            transaction.commit();
        }else if(f instanceof ModeloFragment){
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_fragment_containers,new ModeloFragment());
            fragment = new ModeloFragment();
            transaction.commit();
        }else if(f instanceof AudioFragment){
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_fragment_containers,new AudioFragment());
            fragment = new AudioFragment();
            transaction.commit();
        }

    }


    @Override
    public void onBeaconServiceConnect() {
        final Region region = new Region("myBeaons", Identifier.parse("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);
        beaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                try {
                    Log.d(TAG, "didEnterRegion");
                    beaconManager.startRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void didExitRegion(Region region) {
                try {
                    Log.d(TAG, "didExitRegion");
                    beaconManager.stopRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void didDetermineStateForRegion(int i, Region region) {
            }
        });
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                for(final Beacon oneBeacon : beacons) {
                    Log.d(TAG, "distancia: " + oneBeacon.getDistance() + " id:" + oneBeacon.getId1() + "/" + oneBeacon.getId2() + "/" + oneBeacon.getId3());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_fragment_containers);
                            switch (String.valueOf(oneBeacon.getId2())){
                                case "51626":
                                    // San Juan Evangelista
                                    opcion = "San Juan Evangelista";
                                    Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                                    spinner.setSelectedIndex(0);
                                    recargaFrag(currentFragment);
                                    break;
                                case "10903":
                                    //El crucifijo
                                    //card_desc.setDesc("Esta es la descripcion del paso El Crucifijo");
                                    opcion = "El Crucifijo";
                                    Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                                    spinner.setSelectedIndex(1);

                                    recargaFrag(currentFragment);
                                    break;

                                case "43984":
                                    // Virgen de los Dolores
                                    opcion = "Virgen de los Dolores";
                                    Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                                    spinner.setSelectedIndex(2);

                                    recargaFrag(currentFragment);
                                    break;
                                case "6133":
                                    //El señor del Huerto
                                    opcion = "El Señor del Huerto";
                                    //recargar fragment
                                    Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                                    spinner.setSelectedIndex(3);
                                    recargaFrag(currentFragment);
                                    break;
                            }
                        }
                    });

                }
            }

        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
