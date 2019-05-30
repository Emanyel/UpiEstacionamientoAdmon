package com.emmanuel.upiestacionamiento;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.Result;

import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRShower extends AppCompatActivity implements ZXingScannerView.ResultHandler {


    private ZXingScannerView escanerView;
    TextView info;
    ImageView close;
    Button aceptar;
    String URL = "https://upiicsapark.xyz/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrshower);
        aceptar = (Button)findViewById(R.id.btnAceptar);
    }
    public void leerQR(View view){
     abrirLector();
    }

    public  void abrirLector(){
        escanerView = new ZXingScannerView(this);
        setContentView(escanerView);
        escanerView.setResultHandler(this);
        escanerView.startCamera();

    }
    @Override
    protected void onPause() {
        super.onPause();
        escanerView.stopCamera();
    }


    @Override
    public void handleResult(Result result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogBasicCostumStyle);
        builder.setTitle("Información del Alumno");
        builder.setMessage(result.getText())
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        regstrarEntrada();
                    }
                }).show();
        escanerView.resumeCameraPreview(this); //CIERRA LA VENTANA Y VUELVE  ALA ANTERIOR
    }

    private void regstrarEntrada(String URL) {
        final String boleta = "2016601495", folio="nas456";
        final String validador = "dentro";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Registrado" + response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("boleta", boleta);
                parametros.put("folio", folio);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
