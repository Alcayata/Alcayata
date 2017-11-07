package com.senamoviles.alcayata.alcayata;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.droidbyme.dialoglib.DroidDialog;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.senamoviles.alcayata.alcayata.MainFragments.CitaFragment;
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

import java.io.File;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements BeaconConsumer{



    public static String opcion;
    public static final String TAG = "Semana Santa";
    public static String tagfragment = "";
    FirebaseAuth mAuth = FirebaseAuth.getInstance();




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
    String nombrePaso = "";
    String nombreArchivo = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        opcion = getIntent().getExtras().getString("paso");

        frg = getSupportFragmentManager().findFragmentById(R.id.frame_fragment_containers);

        beaconManager=BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.bind(this);

        bottonNav.setDefaultItem(1);
        spinner.setItems("San Juan Evangelista","El Crucifijo","Virgen de los Dolores","El Señor del Huerto");
        spinner.setText(opcion);

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
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
                        transaction.replace(R.id.frame_fragment_containers,new CitaFragment());
                        fragment = new CitaFragment();
                        break;
                }
                transaction.commit();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (opcion){
            case "San Juan Evangelista":
                nombrePaso = "sanjuan.pdf";
                nombreArchivo = "san_juan_evangelista.pdf";
                break;
            case "El Crucifijo":
                nombrePaso = "crucifijo.pdf";
                nombreArchivo = "el_crucifijo.pdf";
                break;
            case "Virgen de los Dolores":
                nombrePaso = "dolorosa.pdf";
                nombreArchivo = "vigen_de_los_dolores.pdf";
                break;
            case "El Señor del Huerto":
                nombrePaso = "huerto.pdf";
                nombreArchivo = "el_señor_del_huerto.pdf";
                break;
        }
        int id = item.getItemId();
        if(id == R.id.item_descarga){
            new DroidDialog.Builder(this)
                    .icon(R.drawable.ic_juan)
                    .title("Descargar PDF")
                    .content("Estas deacuerdo para descargar el archivo pdf de la nube?")
                    .cancelable(true, true)
                    .color(ContextCompat.getColor(this, R.color.colorPrimaryDark), Color.WHITE, Color.DKGRAY)
                    .positiveButton("descargar", new DroidDialog.onPositiveListener() {
                        @Override
                        public void onPositive(Dialog dialog) {
                            downloadFile();
                            dialog.dismiss();
                        }
                    })
                    .show();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void downloadFile(){

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://alcayata-2017.appspot.com/");
        StorageReference islandRef = storageRef.child("PDF").child(nombrePaso);

        File rootPath = new File(Environment.getExternalStorageDirectory(), "SemanaSantaPopayan");
        if(!rootPath.exists()) {
            rootPath.mkdirs();
        }

        Log.e("firebase ",";El nombre del paso es " +nombrePaso);
        final File localFile = new File(rootPath,nombreArchivo);
        Log.e("firebase ",";El nombre del archivo es " +localFile);

        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.e("firebase ",";archivo local ha sido creado " +localFile.toString());
                Snackbar snackbar = Snackbar.make(findViewById(R.id.viewSnack),"Archivo descargado, desea abrirlo?",Snackbar.LENGTH_LONG)
                        .setAction("OPEN", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                openFile();
                            }
                        });
                snackbar.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.viewSnack),"1archivo local no ha podido ser creado",Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

    }

    public void openFile(){
        File file = new File(Environment.getExternalStorageDirectory(),
                "SemanaSantaPopayan/"+nombreArchivo);
        Uri path = Uri.fromFile(file);
        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
        pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfOpenintent.setDataAndType(path, "application/pdf");
        try {
            startActivity(pdfOpenintent);
        }
        catch (ActivityNotFoundException e) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.viewSnack),"archivo local no ha podido ser abierto",Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    public void recargaFrag(Fragment f){
        if(f instanceof InfoFragment){
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_fragment_containers,new InfoFragment());
            fragment = new InfoFragment();
            transaction.commit();
        }else if(f instanceof CitaFragment){
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_fragment_containers,new CitaFragment());
            fragment = new CitaFragment();
            transaction.commit();

        }else if(f instanceof ModeloFragment){
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_fragment_containers,new ModeloFragment());
            fragment = new ModeloFragment();
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
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, final Region region) {
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
                                    spinner.setSelectedIndex(0);
                                    recargaFrag(currentFragment);
                                    break;

                                    //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();

                                case "10903":
                                    //El crucifijo
                                    //card_desc.setDesc("Esta es la descripcion del paso El Crucifijo");
                                    opcion = "El Crucifijo";
                                    //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                                    spinner.setSelectedIndex(1);
                                    recargaFrag(currentFragment);
                                    break;

                                case "43984":
                                    // Virgen de los Dolores
                                    opcion = "Virgen de los Dolores";
                                    //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                                    spinner.setSelectedIndex(2);
                                    recargaFrag(currentFragment);
                                    break;
                                case "6133":
                                    //El señor del Huerto
                                    opcion = "El Señor del Huerto";
                                    //recargar fragment
                                    //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
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
