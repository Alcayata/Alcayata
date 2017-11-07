package com.senamoviles.alcayata.alcayata.MainFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.senamoviles.alcayata.alcayata.MainActivity;
import com.senamoviles.alcayata.alcayata.ModeloActivity;
import com.senamoviles.alcayata.alcayata.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModeloFragment extends Fragment {


    @BindView(R.id.info_modelo)
    TextView infoModelo;
    Unbinder unbinder;
    @BindView(R.id.imgModelo)
    ImageView imgModelo;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modelo, container, false);
        unbinder = ButterKnife.bind(this, view);
        Button btn_modelo = (Button) view.findViewById(R.id.btn_modelo);
        btn_modelo.setEnabled(false);

        switch (MainActivity.opcion) {
            case "San Juan Evangelista":
                infoModelo.setText(getResources().getString(R.string.info_modeloJuan));
                btn_modelo.setEnabled(true);
                btn_modelo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), ModeloActivity.class));
                    }
                });

                break;
            case "El Crucifijo":
                infoModelo.setText(getResources().getString(R.string.info_modeloCrucifijo));
                btn_modelo.setEnabled(true);
                btn_modelo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), ModeloActivity.class));
                    }
                });
                break;
            case "Virgen de los Dolores":
                infoModelo.setText(getResources().getString(R.string.info_modeloVirgen));
                btn_modelo.setEnabled(true);
                btn_modelo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), ModeloActivity.class));
                    }
                });
                break;
            case "El Señor del Huerto":
                infoModelo.setText(getResources().getString(R.string.info_modeloHuerto));
                btn_modelo.setEnabled(true);
                btn_modelo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), ModeloActivity.class));
                    }
                });
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
