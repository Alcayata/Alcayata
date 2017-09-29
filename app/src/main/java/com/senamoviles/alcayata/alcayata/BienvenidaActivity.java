package com.senamoviles.alcayata.alcayata;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class BienvenidaActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("Bienvenido!","Disfruta una increible experiencia  durante el recorrido procesional",
                R.drawable.logo, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Audifonos","Para disfrutar el audio de la aplicacion utiliza los audifonos",
                R.drawable.logo, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));

        askForPermissions(new String[]{Manifest.permission.BLUETOOTH_ADMIN}, 2);
        addSlide(AppIntroFragment.newInstance("Bluetooth","Necesitas activar el bluetooth para una asistencia automatizada durante el recorrido",
                R.drawable.logo, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));

        showSkipButton(true);

    }
    @Override
    public void onSkipPressed(Fragment currentFragment) {
        //super.onSkipPressed(currentFragment);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }
    @Override
    public void onDonePressed(Fragment currentFragment) {
        //super.onDonePressed(currentFragment);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
