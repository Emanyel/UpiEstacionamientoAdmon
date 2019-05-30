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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLTransactionRollbackException;
import java.util.HashMap;
import java.util.Map;

public class Registrar extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    Intent intent;
    String nombre, contraseña, contra2, apaterno;
    int validar;
    RequestQueue rq;
    JsonRequest jr;
    String URL = "https://upiicsapark.xyz/";
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
                        iniciarSesion(nombre, apaterno, contraseña);
                    }else{
                        Toast.makeText(getApplicationContext(), "Las contraseñas no son iguales", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }


    }

    private void iniciarSesion(String nombre, String apaterno, String contraseña) {
        String sum = "registrar.php?nombre="+nombre+"&apaterno="+apaterno+"&contraseña="+contraseña;
        String urlComp = URL + sum;
        jr = new JsonObjectRequest(Request.Method.GET, urlComp, null, this, this);
        rq.add(jr);

    }

    public void cancelar(View view) {
        intent = new Intent(this, MainActivity.class);
        startActivity(intent
        );
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Users user = new Users();
        validar = 1;
        guardarPreferences(nombre, contraseña,  apaterno);
        Toast.makeText(getApplicationContext(), "Usuario registrado", Toast.LENGTH_SHORT).show();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);
            user.setNombre(jsonObject.optString("nombre"));
            user.setContraseña(jsonObject.optString("contraseña"));
            user.setApaterno(jsonObject.optString("apaterno"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void guardarPreferences(String nombre, String contraseña, String apaterno){
            if(validar == 1){
                Context context = getApplicationContext();
                SharedPreferences sharedPreferences = getSharedPreferences("Admon", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("nombre", nombre);
                editor.putString("contraseña", contraseña);
                editor.putString("apaterno", apaterno);
                editor.commit();
            }else if(validar == 2){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Hubo un inconveniente Intenta más tarde", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

}

