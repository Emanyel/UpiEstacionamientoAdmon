package com.emmanuel.upiestacionamiento;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText et1, et2;
Context context;
String usuario, contras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void ingresar(View view) {
        et1 = (EditText)findViewById(R.id.user);
        et2 = (EditText)findViewById(R.id.password);
        usuario = et1.getText().toString();
        contras = et2.getText().toString();
        SharedPreferences sharedPreferences  = getSharedPreferences("Admon", getApplication().MODE_PRIVATE);
        String user = sharedPreferences.getString("nombre", "N/A");
        String pass = sharedPreferences.getString("contraseña", "N/A");

        if(user.equals(usuario) && pass.equals(contras)){
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
