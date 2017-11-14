package com.senamoviles.alcayata.alcayata;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
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
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.droidbyme.dialoglib.DroidDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
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


public class MainActivity extends AppCompatActivity implements BeaconConsumer {


    private static final int PERMISSION_GROUPSTORAGE = 1;
    public static String opcion;
    public static final String TAG = "Semana Santa";
    public static String tagfragment = "";
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @BindView(R.id.frame_fragment_containers)
    FrameLayout frameFragmentContainers;
    @BindView(R.id.botton_nav)
    BottomNavigation bottonNav;
    Fragment fragment = null;
    Fragment frg = null;
    private FragmentTransaction transaction;
    BoomMenuButton bmb;
    ProgressBar progressBar;


    BeaconManager beaconManager;
    Fragment currentFragment = null;
    String nombrePaso = "";
    String nombreArchivo = "";

    // Storage Permissions variables
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static String[] PERMISSION_GROUP = {
            android.Manifest.permission_group.STORAGE
    };

    //persmission method.
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public static void verifyFilePermision(Activity activity) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasPermission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission_group.STORAGE);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, PERMISSION_GROUP, PERMISSION_GROUPSTORAGE);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        verifyStoragePermissions(this);
        verifyFilePermision(this);

        ActionBar mActionBar = getSupportActionBar();
        assert mActionBar != null;
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View actionBar = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView mTitleTextView = (TextView) actionBar.findViewById(R.id.title_text);
        mTitleTextView.setText(R.string.app_name);
        mActionBar.setCustomView(actionBar);
        mActionBar.setDisplayShowCustomEnabled(true);
        ((Toolbar) actionBar.getParent()).setContentInsetsAbsolute(0, 0);

        bmb = (BoomMenuButton) actionBar.findViewById(R.id.action_bar_left_bmb);
        bmb.setButtonEnum(ButtonEnum.Ham);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);
        bmb.setNormalColor(R.color.white);

        opcion = getIntent().getExtras().getString("paso");

        frg = getSupportFragmentManager().findFragmentById(R.id.frame_fragment_containers);

        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.bind(this);

        bottonNav.setDefaultItem(1);

        HamButton.Builder builder = new HamButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        opcion = "San Juan Evangelista";
                        currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_fragment_containers);
                        recargaFrag(currentFragment);


                    }
                })
                .normalImageRes(R.drawable.ico_juan)
                .normalText("San Juan Evangelista")
                .normalTextColor(Color.rgb(53, 5, 23))
                .normalColor(Color.rgb(255, 255, 255)) //119,72,23
                .highlightedColor(Color.rgb(53, 5, 23))
                .textSize(20);

        bmb.addBuilder(builder);

        HamButton.Builder builder1 = new HamButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        opcion = "El Señor del Huerto";
                        currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_fragment_containers);
                        recargaFrag(currentFragment);

                    }
                })
                .normalImageRes(R.drawable.ico_huerto)
                .normalText("El Señor del Huerto")
                .normalTextColor(Color.rgb(53, 5, 23))
                .normalColor(Color.rgb(255, 255, 255)) //119,72,23
                .highlightedColor(Color.rgb(53, 5, 23))
                .textSize(20);
        bmb.addBuilder(builder1);

        HamButton.Builder builder2 = new HamButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        opcion = "El Crucifijo";
                        currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_fragment_containers);
                        recargaFrag(currentFragment);
                    }
                })
                .normalImageRes(R.drawable.ico_crucifijo)
                .normalText("El Crucifijo")
                .normalTextColor(Color.rgb(53, 5, 23))
                .normalColor(Color.rgb(255, 255, 255)) //119,72,23
                .highlightedColor(Color.rgb(53, 5, 23))
                .textSize(20);
        bmb.addBuilder(builder2);

        HamButton.Builder builder3 = new HamButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        opcion = "Virgen de los Dolores";
                        currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_fragment_containers);
                        recargaFrag(currentFragment);
                    }
                })
                .normalImageRes(R.drawable.ico_dolorosa)
                .normalText("Virgen de los Dolores")
                .normalTextColor(Color.rgb(53, 5, 23))
                .normalColor(Color.rgb(255, 255, 255)) //119,72,23
                .highlightedColor(Color.rgb(53, 5, 23))
                .textSize(20);
        bmb.addBuilder(builder3);


        bottonNav.setOnSelectedItemChangeListener(new OnSelectedItemChangeListener() {
            @Override
            public void onSelectedItemChanged(int i) {
                switch (i) {
                    case R.id.item_3d:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers, new ModeloFragment());
                        fragment = new ModeloFragment();
                        break;
                    case R.id.item_paso:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers, new InfoFragment());
                        fragment = new InfoFragment();
                        break;
                    case R.id.item_cita:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers, new CitaFragment());
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
        switch (opcion) {
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
        if (id == R.id.item_descarga) {
            new DroidDialog.Builder(this)
                    .icon(R.drawable.ic_pdf)
                    .title("Descargar PDF")
                    .content("Deseas descargar el archivo PDF a tu télefono?")
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

    public void downloadFile() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://alcayata-2017.appspot.com/");
        StorageReference islandRef = storageRef.child("PDF").child(nombrePaso);

        File rootPath = new File(Environment.getExternalStorageDirectory(), "SemanaSantaPopayan");
        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }

        Log.e("firebase ", ";El nombre del paso es " + nombrePaso);
        final File localFile = new File(rootPath, nombreArchivo);
        Log.e("firebase ", ";El nombre del archivo es " + localFile);

        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.e("firebase ", ";archivo local ha sido creado " + localFile.toString());
                Snackbar snackbar = Snackbar.make(findViewById(R.id.viewSnack), "Archivo descargado, desea abrirlo?", Snackbar.LENGTH_LONG)
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
                Snackbar snackbar = Snackbar.make(findViewById(R.id.viewSnack), "1archivo local no ha podido ser creado", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

    }

    public void openFile() {
        File file = new File(Environment.getExternalStorageDirectory(),
                "SemanaSantaPopayan/"+nombreArchivo);


        Uri path;
        if(Build.VERSION.SDK_INT >= 23){
            path = FileProvider.getUriForFile(MainActivity.this,
                    getApplicationContext().getPackageName() + ".provider",
                    file);
        } else{
            path = Uri.fromFile(file);
        }
        //Uri path = FileProvider.getUriForFile(MainActivity.this, getPackageName() + ".provider", file);
        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
        pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfOpenintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
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
                                    recargaFrag(currentFragment);
                                    break;

                                    //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();

                                case "10903":
                                    //El crucifijo
                                    //card_desc.setDesc("Esta es la descripcion del paso El Crucifijo");
                                    opcion = "El Crucifijo";
                                    //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                                    recargaFrag(currentFragment);
                                    break;

                                case "43984":
                                    // Virgen de los Dolores
                                    opcion = "Virgen de los Dolores";
                                    //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                                    recargaFrag(currentFragment);
                                    break;
                                case "6133":
                                    //El señor del Huerto
                                    opcion = "El Señor del Huerto";
                                    //recargar fragment
                                    //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
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
