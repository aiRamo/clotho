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

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class CategorySelector extends AppCompatActivity {

    public static final int PERMISSION_CODE = 1000;
    Button TakePhoto;
    Uri image_uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selector);

        TakePhoto = findViewById(R.id.btn_takePhoto);
        Intent imagePreview = new Intent(CategorySelector.this, PreviewHandler.class);


        TakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(imagePreview);


            }
        });
        //Checks to see if pants button is clicked and will than show the take photo button
        Button b = (Button) findViewById(R.id.btn_takePhoto);
        ToggleButton pantsbutton = (ToggleButton) findViewById(R.id.Pants);
        pantsbutton.setOnClickListener( new View.OnClickListener()  {
            public void onClick(View v) {
                b.setVisibility(View.VISIBLE);
                System.out.println("Pants Selector Clicked");
            }
        });
        //Checks shirts
        ToggleButton shirtbutton = (ToggleButton) findViewById(R.id.button3);
        shirtbutton.setOnClickListener( new View.OnClickListener()  {
            public void onClick(View v) {
                b.setVisibility(View.VISIBLE);
                System.out.println("Shirts Selector Clicked");
            }
        });
        //Checks shoes
        ToggleButton shoebutton = (ToggleButton) findViewById(R.id.button3);
        shoebutton.setOnClickListener( new View.OnClickListener()  {
            public void onClick(View v) {
                b.setVisibility(View.VISIBLE);
                System.out.println("Shoes Selector Clicked");
            }
        });

    }

}

