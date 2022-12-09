package com.visionapi.clotho;



import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<DataSnapshot> savedData = new ArrayList<>();

    ArrayList<ImageButton> imageButtonList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent loginPage = new Intent(MainActivity.this, LogIn.class);

        final ImageButton button1 = (ImageButton) findViewById(R.id.outfit1);
        final ImageButton button2 = (ImageButton) findViewById(R.id.outfit2);
        final ImageButton button3 = (ImageButton) findViewById(R.id.outfit3);
        final ImageButton button4 = (ImageButton) findViewById(R.id.outfit4);
        final ImageButton button5 = (ImageButton) findViewById(R.id.outfit5);


        imageButtonList.add(button1);
        imageButtonList.add(button2);
        imageButtonList.add(button3);
        imageButtonList.add(button4);
        imageButtonList.add(button5);

        savedData = GlobalVars.savedData;

        Uri uri;


        if (GlobalVars.savedCount != 0){

            for (int i = 0; i < GlobalVars.savedCount; i++){
                uri = Uri.parse((String) savedData.get(i).child("imageuri").getValue());
                imageButtonList.get(i).setImageURI(uri);
            }

        }



        if (!GlobalVars.isLoggedIn){
            startActivity(loginPage);
        }


    //Settings Button
        ImageButton settingsButton = (ImageButton)findViewById(R.id.setting);
        settingsButton.setOnClickListener( new View.OnClickListener()  {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Settings.class));
                System.out.println("Settings Button Clicked");
            }
        });

        Button categoryButton = findViewById(R.id.btn_newEntry);
        //Category Selection
        categoryButton.setOnClickListener( new View.OnClickListener()  {
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MainActivity.this, CategorySelector.class));
                System.out.println("Category Button Clicked");
            }
        });
        categoryButton.setBackgroundColor(Color.BLUE);

        imageButtonList.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalVars.savedCount >= 1) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse((String) savedData.get(0).child("amazonLink").getValue()));
                    startActivity(i);
                }
            }
        });

        imageButtonList.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalVars.savedCount >= 2) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse((String) savedData.get(1).child("amazonLink").getValue()));
                    startActivity(i);
                }
            }
        });

        imageButtonList.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalVars.savedCount >= 3) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse((String) savedData.get(2).child("amazonLink").getValue()));
                    startActivity(i);
                }
            }
        });

        imageButtonList.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalVars.savedCount >= 4) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse((String) savedData.get(3).child("amazonLink").getValue()));
                    startActivity(i);
                }
            }
        });

        imageButtonList.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalVars.savedCount == 5) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse((String) savedData.get(4).child("amazonLink").getValue()));
                    startActivity(i);
                }
            }
        });


    }
}