package com.senamoviles.alcayata.alcayata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gerard.UnityPlayerActivity;

public class ModeloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modelo);

        startActivity(new Intent(ModeloActivity.this,UnityPlayerActivity.class));
    }
}
