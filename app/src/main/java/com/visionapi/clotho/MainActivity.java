package com.visionapi.clotho;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button newEntryButton = findViewById(R.id.btn_newEntry);
        Intent imagePreview = new Intent(MainActivity.this, PreviewHandler.class);

        newEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(imagePreview);
            }
        });
        ImageButton settingsButton = (ImageButton)findViewById(R.id.setting);
        settingsButton.setOnClickListener( new View.OnClickListener()  {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Settings.class));
                System.out.println("Settings Button Clicked");
            }
        });
        Button categoryButton = (Button)findViewById(R.id.btn_newEntry);
        categoryButton.setOnClickListener( new View.OnClickListener()  {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PreviewHandler.class));
                System.out.println("Category Button Clicked");
            }
        });
    }

}