package com.emmanuel.upiestacionamiento;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Registrar extends AppCompatActivity {
    Intent intent;
    String nombre, contraseña, contra2, apaterno;
    int validar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);



    }




    public void registrar(View view) {
        nombre = ((EditText)findViewById(R.id.nombre)).getText().toString();
        contraseña = ((EditText)findViewById(R.id.password)).getText().toString();
        contra2 = ((EditText)findViewById(R.id.txtConfirmar)).getText().toString();
        apaterno = ((EditText)findViewById(R.id.apaterno)).getText().toString();
        if(nombre.equals("")){
            Toast.makeText(getApplicationContext(), "El campo nombre está vacío", Toast.LENGTH_SHORT).show();
        }else{
            if(contraseña.equals("")){
                Toast.makeText(getApplicationContext(), "El campo contraseña está vacío", Toast.LENGTH_SHORT).show();
            }else {
                if (contra2.equals("")){
                    Toast.makeText(getApplicationContext(), "Valida la contraseña", Toast.LENGTH_SHORT).show();
                }else{
                    if(contraseña.equals(contra2)){
                        registrarUsuario(nombre, contraseña, apaterno, "https://upiiparking.000webhostapp.com/");
                        guardarContraseña(contraseña);
                    }else{
                        Toast.makeText(getApplicationContext(), "Las contraseñas no son iguales", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }


    }

    private void guardarContraseña(String contraseña) {
        if(validar == 1){
            Context context = getApplicationContext();
            SharedPreferences sharedPreferences = getSharedPreferences("Login", context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("nombres", nombre);
            editor.putString("password", contraseña);
            editor.commit();
        }else if(validar == 2){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "No puede hacer uso de la aplicación \n\n Lo sentimos mucho :(", Toast.LENGTH_SHORT).show();
            finish();
        }

        }


    private void registrarUsuario(final String nombre, final String contraseña, final String apaterno, String URL) {
        String sum = "connect.php?names="+nombre+"&apaterno="+apaterno+"&pwd="+contraseña;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL+sum, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                validar = 1;
                Toast.makeText(getApplicationContext(), "Se registró, felicidades" , Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                validar=2;
                Toast.makeText(getApplicationContext(), "No se pudo registrar el usuario" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nombres",nombre);
                params.put("apaterno", apaterno);
                params.put("pwd", contraseña);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void cancelar(View view) {
        intent = new Intent(this, MainActivity.class);
        startActivity(intent
        );
    }
}
