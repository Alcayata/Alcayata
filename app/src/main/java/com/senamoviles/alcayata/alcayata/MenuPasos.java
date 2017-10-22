package com.senamoviles.alcayata.alcayata;

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

    private BoomMenuButton bmb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pasos);
        bmb = (BoomMenuButton) findViewById(R.id.bmb);
        assert bmb != null;
        bmb.setButtonEnum(ButtonEnum.Ham);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);

        HamButton.Builder builder = new HamButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent intent=new Intent(MenuPasos.this, MainActivity.class);
                        startActivity(intent);
                        //Toast.makeText(MenuPasos.this,"presionado: "+index,Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MenuPasos.this,"presionado: "+index,Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MenuPasos.this,"presionado: "+index,Toast.LENGTH_SHORT).show();
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
                        //Toast.makeText(MainActivity.this,"presionado: "+index,Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MenuPasos.this, MainActivity.class);
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
