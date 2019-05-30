package com.emmanuel.upiestacionamiento;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class registrarBD extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
int validar;
String nombre, apaterno, contraseña;
    public void iniciarSesion(){
        Context context = null;
        SharedPreferences sharedPreferences = (SharedPreferences) getSharedPreferences("Admon", context.MODE_PRIVATE);
        nombre = sharedPreferences.getString("nombre", "No haynombre");
        apaterno = sharedPreferences.getString("apaterno", "No hay apellido");
        contraseña = sharedPreferences.getString("contraseña", "No contraseña");

        String URL = "https://upiicsapark.xyz/";
        RequestQueue rq = null;
        JsonRequest jr;

        String sum = "registrar.php?nombre="+nombre+"&apaterno="+apaterno+"contraseña="+contraseña;
        String urlComp = URL + sum;
        jr = new JsonObjectRequest(Request.Method.GET, urlComp, null, this, this);
        rq.add(jr);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        validar = 2;
        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Users user = new Users();
        validar = 1;
        guardarPreferences(nombre, apaterno, contraseña);
        Toast.makeText(getApplicationContext(), "Felicidades, ahora inicia sesión", Toast.LENGTH_SHORT).show();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);
            user.setNombre(jsonObject.optString("nombre"));
            user.setApaterno(jsonObject.optString("apaterno"));
            user.setContraseña(jsonObject.optString("contraseña"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void guardarPreferences(String nombre, String apaterno, String contraseña){
        if(validar == 1){
            Context context = getApplicationContext();
            SharedPreferences sharedPreferences = getSharedPreferences("Admon", context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("nombre", this.nombre);
            editor.putString("apaterno", this.apaterno);
            editor.putString("contraseña", this.contraseña);
            editor.commit();
        }else if(validar == 2){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Algo salio mal, intenta mas tarde", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
