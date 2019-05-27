package com.emmanuel.upiestacionamiento;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Registrar extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
    }

    public void registrar(View view) {
        intent = new Intent(this, QRShower.class);
        startActivity(intent);

    }

    public void cancelar(View view) {
        intent = new Intent(this, MainActivity.class);
        startActivity(intent
        );
    }
}
