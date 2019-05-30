package com.emmanuel.upiestacionamiento;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Splash extends AppCompatActivity {

    private FragmentActivity activity;
    private final String Write = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String Read = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String Camera = Manifest.permission.CAMERA;
    private final String Internet = Manifest.permission.INTERNET;

    private final static int REQUEST_PERMISSIONS = 200;
    private String[] params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity = Splash.this;
        Stetho.initializeWithDefaults(activity);
        prepareComponents();
    }




    private void prepareComponents() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Build.VERSION.SDK_INT >=  Build.VERSION_CODES.M){
                    requestPermission();
                }else {
                    Continue();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission() {

        int hasWritePermission = activity.checkSelfPermission(Write);
        int hasInternetPermission = activity.checkSelfPermission(Internet);
        int hasReadPermission = activity.checkSelfPermission(Read);
        int hasCameraPermission = activity.checkSelfPermission(Camera);

        List<String> permissions = new ArrayList<>();
        if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Write);
        }
        if (hasInternetPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Internet);
        }
        if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Read);
        }
        if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Camera);
        }
        if (!permissions.isEmpty()) {
            params = permissions.toArray(new String[permissions.size()]);
            AlertPermission();
        } else {
            Continue();
        }
    }



    private void Continue() {
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void AlertPermission() {
        new AlertDialog.Builder(activity).setTitle(Utils.getStr(activity, R.string.title_atention)).setMessage(Utils.getStr(activity, R.string.msg_permission))
                .setPositiveButton(Utils.getStr(activity, R.string.title_accept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(params, REQUEST_PERMISSIONS);
                        }
                    }
                })
                .setNegativeButton(Utils.getStr(activity, R.string.title_exit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setCancelable(false).show();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int hasWritePermission = activity.checkSelfPermission(Write);
        int hasInternetPermission = activity.checkSelfPermission(Internet);
        int hasReadPermission = activity.checkSelfPermission(Read);
        int hasCameraPermission = activity.checkSelfPermission(Camera);
        boolean move = true;
        if (hasReadPermission != 0) {
            move=false;
        }
        if (hasWritePermission != 0) {
            move=false;
        }
        if(hasCameraPermission != 0) {
            move=false;
        }
        if(hasInternetPermission != 0){
            move=false;
        }

        if(move) {
            prepareComponents();
        } else {
            finish();
        }
    }

}
