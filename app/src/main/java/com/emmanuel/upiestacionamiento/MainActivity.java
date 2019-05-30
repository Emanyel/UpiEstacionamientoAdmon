package com.emmanuel.upiestacionamiento;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText et1, et2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ingresar(View view) {
        leerPreferences();
        Intent intent;
        intent = new Intent(this, QRShower.class);
        startActivity(intent);
    }

    private void leerPreferences() {


        SharedPreferences sharedPreferences  = getSharedPreferences("Login", MODE_PRIVATE);
        String user = sharedPreferences.getString("nombres", "N/A");
        String pass = sharedPreferences.getString("password", "N/A");

        if(user.equals(et1.getText().toString()) && pass.equals(et2.getText().toString())){
            Intent intent = new Intent(getApplicationContext(), QRShower.class);
            Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "Usuario y/o Contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }

    public void registrar(View view) {
        Intent intent;
        intent = new Intent(this, Registrar.class);
        startActivity(intent);
    }
}
