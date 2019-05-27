package com.emmanuel.upiestacionamiento;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ingresar(View view) {
        Intent intent;
        intent = new Intent(this, QRShower.class);
        startActivity(intent);
    }

    public void registrar(View view) {
        Intent intent;
        intent = new Intent(this, Registrar.class);
        startActivity(intent);
    }
}
