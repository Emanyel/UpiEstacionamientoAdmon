package com.emmanuel.upiestacionamiento;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
    Button aceptar;

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
                        Toast.makeText(getApplicationContext(), "Registrado", Toast.LENGTH_SHORT).show();
                    }
                }).show();
        escanerView.resumeCameraPreview(this); //CIERRA LA VENTANA Y VUELVE  ALA ANTERIOR
    }
}
