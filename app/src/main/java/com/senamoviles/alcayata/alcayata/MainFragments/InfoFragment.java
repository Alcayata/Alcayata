package com.senamoviles.alcayata.alcayata.MainFragments;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidbyme.dialoglib.DroidDialog;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.senamoviles.alcayata.alcayata.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.senamoviles.alcayata.alcayata.MainActivity.opcion;

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
    @BindView(R.id.btnLeer)
    Button btnLeer;
    @BindView(R.id.btnEscuchar)
    Button btnEscuchar;
    @BindView(R.id.nombre_paso)
    TextView nombrePaso;

    private String sabias;
    private String sabiasMas;
    Dialog dialog;


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
        imgPaso.setClickable(true);

        switch (opcion) {
            case "San Juan Evangelista":
                nombrePaso.setText("San Juan Evangelista");
                txtDesc.setText(getResources().getText(R.string.descripcionJuan));
                imgPaso.setImageResource(R.drawable.juan);
                sabias = getResources().getString(R.string.sabiasJuan);
                sabiasMas = getResources().getString(R.string.sabiasMasJuan);
                break;
            case "El Crucifijo":
                nombrePaso.setText("El Crucifijo");
                txtDesc.setText(getResources().getString(R.string.descripcionCrucifijo));
                imgPaso.setImageResource(R.drawable.crucifijo);
                sabias = getResources().getString(R.string.sabiasCrucifijo);
                sabiasMas = getResources().getString(R.string.sabiasMasCrucifijo);
                break;
            case "Virgen de los Dolores":
                nombrePaso.setText("Virgen de los Dolores");
                txtDesc.setText(getResources().getString(R.string.descripcionVirgen));
                imgPaso.setImageResource(R.drawable.virgen);
                sabias = getResources().getString(R.string.sabiasVirgen);
                sabiasMas = getResources().getString(R.string.sabiasMasVirgen);
                break;
            case "El Señor del Huerto":
                nombrePaso.setText("El Señor del Huerto");
                txtDesc.setText(getResources().getString(R.string.descripcionhuerto));
                imgPaso.setImageResource(R.drawable.huerto);
                sabias = getResources().getString(R.string.sabiasHuerto);
                sabiasMas = getResources().getString(R.string.sabiasMasHuerto);
                break;
        }

        imgPaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarImg();
            }
        });


        btnLeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DroidDialog.Builder(getActivity())
                        .icon(R.drawable.ic_juan)
                        .title("¿Sabias Qué?")
                        .content(sabias)
                        .cancelable(true, true)
                        .color(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark), Color.WHITE, Color.DKGRAY)
                        .positiveButton("Leer más", new DroidDialog.onPositiveListener() {
                            @Override
                            public void onPositive(Dialog dialog) {
                                dialog.dismiss();
                                new BottomDialog.Builder(getActivity())

                                        .setContent(sabiasMas)
                                        .show();
                            }
                        })
                        .show();
            }
        });

        btnEscuchar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarPlayer();
            }
        });

        return view;
    }

    private void mostrarPlayer() {
        FragmentManager fm = getFragmentManager();
        AudioFragment dialogFragment = new AudioFragment();
        dialogFragment.show(fm, "Sample Fragment");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void mostrarImg() {
        dialog = new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.image_dialog);

        ImageView imageView = (ImageView) dialog.findViewById(R.id.imgDialog);
        switch (opcion) {
            case "San Juan Evangelista":
                imageView.setImageResource(R.drawable.juan);
                break;
            case "El Crucifijo":
                imageView.setImageResource(R.drawable.crucifijo);
                break;
            case "Virgen de los Dolores":
                imageView.setImageResource(R.drawable.virgen);
                break;
            case "El Señor del Huerto":
                imageView.setImageResource(R.drawable.huerto);
                break;
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
    }


}
