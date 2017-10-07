package com.senamoviles.alcayata.alcayata.MainFragments;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.senamoviles.alcayata.alcayata.MainActivity;
import com.senamoviles.alcayata.alcayata.ModeloActivity;
import com.senamoviles.alcayata.alcayata.R;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class DescargaFragment extends Fragment {

    View view;
    String nombrePaso = "";
    String nombreArchivo = "";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_descarga, container, false);
        Button btn_descargar = (Button) view.findViewById(R.id.btn_descarga);
        switch (MainActivity.opcion){
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
        btn_descargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile();
            }
        });

        return view;
    }

    public void downloadFile(){

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://alcayata-2017.appspot.com");
        StorageReference  islandRef = storageRef.child("PDF").child(nombrePaso);
        File rootPath = new File(Environment.getExternalStorageDirectory(), "SemanaSantaPopayan");
        if(!rootPath.exists()) {
            rootPath.mkdirs();
        }
        final File localFile = new File(rootPath,nombreArchivo);
        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.e("firebase ",";local tem file created  created " +localFile.toString());
                Snackbar snackbar = Snackbar.make(view.findViewById(R.id.frag_descanrga),"Archivo descargado, desea abrirlo?",Snackbar.LENGTH_LONG)
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
                Log.e("firebase ",";local tem file not created  created " + e.toString());
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
            Log.e("firebase ",";local tem file not created  created " + e.toString());
        }
    }

}
