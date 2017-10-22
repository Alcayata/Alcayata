package com.senamoviles.alcayata.alcayata;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.droidbyme.dialoglib.AnimUtils;
import com.droidbyme.dialoglib.DroidDialog;
import com.eminayar.panter.PanterDialog;
import com.eminayar.panter.enums.Animation;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;

public class DiaProcesiones extends AppCompatActivity {

    private BoomMenuButton bmb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_procesiones);


        bmb = (BoomMenuButton) findViewById(R.id.bmb);
        assert bmb != null;
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_6_1);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_6_1);


        TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent intent = new Intent(getApplicationContext(), MenuPasos.class);
                        startActivity(intent);
                        /*new PanterDialog(DiaProcesiones.this)
                                .setHeaderBackground(R.drawable.pattern_bg_blue)
                                .setHeaderLogo(R.drawable.sample_logo)
                                .setPositive("OK")// Se puede agregar View.OnClickListener como segundo parametro
                                //.setNegative("CANCELAR")
                                .setMessage(R.string.sabiasque)
                                .isCancelable(false)
                                .withAnimation(Animation.POP)
                                .show();*/
                    }
                })
                .normalImageRes(R.drawable.a)
                .normalText("Viernes de dolores")
                .normalTextColor(Color.rgb(251,192,45))
                .normalColor(Color.rgb(53,5,23)) //119,72,23
                .highlightedColor(Color.rgb(251,192,45))
                .isRound(false)
                .shadowCornerRadius(Util.dp2px(15))
                .buttonCornerRadius(Util.dp2px(15))
                ;


        bmb.addBuilder(builder);

        TextOutsideCircleButton.Builder builder2 = new TextOutsideCircleButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        new BottomDialog.Builder(DiaProcesiones.this)
                                .setTitle("¿Sabías Qué?")
                                .setContent("¿Sabías que vas a perder la especialización si no acabas rápido este código?")
                                .setIcon(R.drawable.cruzamarilla)
                                .show();
                    }
                })
                .normalImageRes(R.drawable.b)
                .normalText("Martes: Nuestra Señora la Virgen de los Dolores")
                .normalTextColor(Color.rgb(251,192,45))
                .normalColor(Color.rgb(53,5,23)) //119,72,23
                .highlightedColor(Color.rgb(251,192,45))
                .isRound(false)
                .shadowCornerRadius(Util.dp2px(15))
                .buttonCornerRadius(Util.dp2px(15))
                ;
        bmb.addBuilder(builder2);

        TextOutsideCircleButton.Builder builder3 = new TextOutsideCircleButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        DroidDialog dialogo = new DroidDialog.Builder(DiaProcesiones.this)
                                .icon(R.drawable.cruzamarilla)
                                .title("¿Sabías qué?")
                                .content(getString(R.string.sabiasque))
                                .cancelable(true,true)
                                .animation(AnimUtils.AnimZoomInOut)
                                .color(Color.rgb(53,5,23),Color.rgb(251,192,45), Color.rgb(251,192,45))
                                .positiveButton("OK", new DroidDialog.onPositiveListener() {
                                    @Override
                                    public void onPositive(Dialog droidDialog) {

                                    }
                                })
                                .show();


                    }
                })
                .normalImageRes(R.drawable.c)
                .normalText("Miércoles: El Amo Jesús")
                .normalTextColor(Color.rgb(251,192,45))
                .normalColor(Color.rgb(53,5,23)) //119,72,23
                .highlightedColor(Color.rgb(251,192,45))
                .isRound(false)
                .shadowCornerRadius(Util.dp2px(15))
                .buttonCornerRadius(Util.dp2px(15))
                ;
        bmb.addBuilder(builder3);

        TextOutsideCircleButton.Builder builder4 = new TextOutsideCircleButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Toast.makeText(DiaProcesiones.this,"presionado: "+index,Toast.LENGTH_SHORT).show();
                    }
                })
                .normalImageRes(R.drawable.d)
                .normalText("Jueves: El Señor de la Veracruz")
                .normalTextColor(Color.rgb(251,192,45))
                .normalColor(Color.rgb(53,5,23)) //119,72,23
                .highlightedColor(Color.rgb(251,192,45))
                .isRound(false)
                .shadowCornerRadius(Util.dp2px(15))
                .buttonCornerRadius(Util.dp2px(15))
                ;
        bmb.addBuilder(builder4);

        TextOutsideCircleButton.Builder builder5 = new TextOutsideCircleButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Toast.makeText(DiaProcesiones.this,"presionado: "+index,Toast.LENGTH_SHORT).show();
                    }
                })
                .normalImageRes(R.drawable.e)
                .normalText("Viernes: Santo Entierro de Cristo")
                .normalTextColor(Color.rgb(251,192,45))
                .normalColor(Color.rgb(53,5,23)) //119,72,23
                .highlightedColor(Color.rgb(251,192,45))
                .isRound(false)
                .shadowCornerRadius(Util.dp2px(15))
                .buttonCornerRadius(Util.dp2px(15))
                ;
        bmb.addBuilder(builder5);

        TextOutsideCircleButton.Builder builder6 = new TextOutsideCircleButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Toast.makeText(DiaProcesiones.this,"presionado: "+index,Toast.LENGTH_SHORT).show();
                    }
                })
                .normalImageRes(R.drawable.f)
                .normalText("Sábado: Nuestro Señor Jesucristo Resucitado")
                .normalTextColor(Color.rgb(251,192,45))
                .normalColor(Color.rgb(53,5,23)) //119,72,23
                .highlightedColor(Color.rgb(251,192,45))
                .isRound(false)
                .shadowCornerRadius(Util.dp2px(15))
                .buttonCornerRadius(Util.dp2px(15))
                ;
        bmb.addBuilder(builder6);
    }
}
