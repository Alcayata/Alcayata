package com.senamoviles.alcayata.alcayata.MainFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.senamoviles.alcayata.alcayata.MainActivity;
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
