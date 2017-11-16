package com.senamoviles.alcayata.alcayata.MainFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
public class CitaFragment extends Fragment {


    @BindView(R.id.img_cita)
    ImageView imgCita;
    @BindView(R.id.txt_cita)
    TextView txt_cita;
    Unbinder unbinder;

    // TODO 1: Cambiar las imagenes de las citas biblicas

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flores, container, false);
        unbinder = ButterKnife.bind(this, view);

        switch (MainActivity.opcion) {
            case "San Juan Evangelista":
                imgCita.setImageResource(R.drawable.ref_juan_new);
                txt_cita.setText(getResources().getString(R.string.refJuan));

                break;
            case "El Crucifijo":
                imgCita.setImageResource(R.drawable.ref_crucifijo_new);
                txt_cita.setText(getResources().getString(R.string.refCrucifijo));
                break;
            case "Virgen de los Dolores":
                imgCita.setImageResource(R.drawable.ref_virgen_new);
                txt_cita.setText(getResources().getString(R.string.refVirgen));
                break;
            case "El Señor del Huerto":
                imgCita.setImageResource(R.drawable.ref_huerto_new);
                txt_cita.setText(getResources().getString(R.string.refHuerto));
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
