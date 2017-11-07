package com.senamoviles.alcayata.alcayata;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

public class MenuPasos extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private BoomMenuButton bmb;
    String paso = "paso";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pasos);
        bmb = (BoomMenuButton) findViewById(R.id.bmb);
        assert bmb != null;
        bmb.setButtonEnum(ButtonEnum.Ham);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);
        bmb.setAutoBoom(true);


        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Tu dispositivo no soporta bluetooth", Toast.LENGTH_SHORT).show();
        }
        else{
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }






        HamButton.Builder builder = new HamButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent intent = new Intent(MenuPasos.this,MainActivity.class);
                        intent.putExtra(paso,"San Juan Evangelista");
                        startActivity(intent);

                    }
                })
                .normalImageRes(R.drawable.cruzamarilla)
                .normalText("San Juan Evangelista")
                .normalTextColor(Color.rgb(251,192,45))
                .normalColor(Color.rgb(53,5,23)) //119,72,23
                .highlightedColor(Color.rgb(251,192,45))
                .subNormalText("Imagen española del siglo XVIII");

        bmb.addBuilder(builder);

        HamButton.Builder builder1 = new HamButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent intent = new Intent(MenuPasos.this,MainActivity.class);
                        intent.putExtra(paso,"El Señor del Huerto");
                        startActivity(intent);

                    }
                })
                .normalImageRes(R.drawable.cruzamarilla)
                .normalText("El Señor del Huerto")
                .normalTextColor(Color.rgb(251,192,45))
                .normalColor(Color.rgb(53,5,23))
                .highlightedColor(Color.rgb(251,192,45))
                .subNormalText("Talla quiteña del siglo XVII");
        bmb.addBuilder(builder1);

        HamButton.Builder builder2 = new HamButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent intent = new Intent(MenuPasos.this,MainActivity.class);
                        intent.putExtra(paso,"El Crucifijo");
                        startActivity(intent);
                    }
                })
                .normalImageRes(R.drawable.cruzamarilla)
                .normalText("El Crucifijo")
                .normalColor(Color.rgb(53,5,23))
                .normalTextColor(Color.rgb(251,192,45))
                .highlightedColor(Color.rgb(251,192,45))
                .subNormalText("Imagene española del siglo XVIII");
        bmb.addBuilder(builder2);

        HamButton.Builder builder3 = new HamButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent intent = new Intent(MenuPasos.this,MainActivity.class);
                        intent.putExtra(paso,"Virgen de los Dolores");
                        startActivity(intent);

                    }
                })
                .normalImageRes(R.drawable.cruzamarilla)
                .normalText("Virgen de los Dolores")
                .normalColor(Color.rgb(53,5,23))
                .normalTextColor(Color.rgb(251,192,45))
                .highlightedColor(Color.rgb(251,192,45))
                .subNormalText("Imágen colombiana del siglo XX");
        bmb.addBuilder(builder3);
    }
}
