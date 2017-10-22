package com.senamoviles.alcayata.alcayata.MainFragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.droidbyme.dialoglib.DroidDialog;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.senamoviles.alcayata.alcayata.MainActivity;
import com.senamoviles.alcayata.alcayata.Paso;
import com.senamoviles.alcayata.alcayata.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {

    @BindView(R.id.img_paso)
    ImageView imgPaso;
    @BindView(R.id.txt_desc)
    TextView txtDesc;
    @BindView(R.id.card_sabias)
    TextView cardSabias;
    Unbinder unbinder;

    String imagen = "drawable/";


    //referencias a la base de datos Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("pasos");
    ProgressDialog pDialog;
    boolean isImageFitToScreen;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        unbinder = ButterKnife.bind(this, view);

        /*pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Cargando...");

        pDialog.show();
        Query query = myRef.orderByChild("nombre").equalTo(MainActivity.opcion);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Paso paso = dataSnapshot.getValue(Paso.class);
                txtDesc.setText(paso.getDescripcion());
                cardSabias.setText("Sabias Que...");
                int resId = getResources().getIdentifier(imagen+paso.getImagen(),"drawable", getActivity().getPackageName());
                imgPaso.setImageResource(resId);
                pDialog.dismiss();

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });*/

        cardSabias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DroidDialog.Builder(getActivity())
                        .icon(R.drawable.ic_juan)
                        .title("Sabias Qué?")
                        .content("¿Sabías que vas a perder la especialización si no acabas rápido este código?")
                        .cancelable(true,true)
                        .positiveButton("Leer más", new DroidDialog.onPositiveListener() {
                            @Override
                            public void onPositive(Dialog dialog) {
                                dialog.dismiss();
                                new BottomDialog.Builder(getActivity())
                                        .setTitle("Awesome!")
                                        .setContent("What can we improve? Your feedback is always welcome.")
                                        .show();
                            }
                        })
                        .show();


            }
        });



        switch (MainActivity.opcion){
            case "San Juan Evangelista":
                txtDesc.setText(getResources().getText(R.string.descripcionJuan));
                cardSabias.setText(getResources().getText(R.string.sabiasJuan));
                imgPaso.setImageResource(R.drawable.juan);

                break;
            case "El Crucifijo":
                txtDesc.setText(getResources().getString(R.string.descripcionCrucifijo));
                cardSabias.setText(getResources().getString(R.string.sabiasCrucifijo));
                imgPaso.setImageResource(R.drawable.crucifijo);
                break;
            case "Virgen de los Dolores":
                txtDesc.setText(getResources().getString(R.string.descripcionVirgen));
                cardSabias.setText(getResources().getString(R.string.sabiasVirgen));
                imgPaso.setImageResource(R.drawable.virgen);
                break;
            case "El Señor del Huerto":
                txtDesc.setText(getResources().getString(R.string.descripcionhuerto));
                cardSabias.setText(getResources().getString(R.string.sabiasHuerto));
                imgPaso.setImageResource(R.drawable.huerto);
                break;
        }

        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
