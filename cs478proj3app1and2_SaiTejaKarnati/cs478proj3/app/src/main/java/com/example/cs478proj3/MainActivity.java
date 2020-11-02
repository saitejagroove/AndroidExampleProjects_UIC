package com.example.cs478proj3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private  int OUR_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 =  findViewById(R.id.button1);
        Button button2 =  findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.project3) == PackageManager.PERMISSION_GRANTED){
                    //Toast.makeText(MainActivity.this,"you already have permission",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setAction("com.cs478.app2");
                    intent.putExtra("item",0);
                    intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    sendBroadcast(intent);
                }else{
                    requestProjPermissions();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.project3) == PackageManager.PERMISSION_GRANTED){
                    //Toast.makeText(MainActivity.this,"you already have permission",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setAction("com.cs478.app2");
                    intent.putExtra("item",1);
                    intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    sendBroadcast(intent);
                }else{
                    requestProjPermissions();
                }
            }
        });
    }

    private void requestProjPermissions(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.project3)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.project3},OUR_PERMISSION);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.project3},OUR_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==OUR_PERMISSION){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this,"permission granted",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this,"permission denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
