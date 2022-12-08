package com.visionapi.clotho;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent loginPage = new Intent(MainActivity.this, LogIn.class);


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
                startActivity(new Intent(MainActivity.this, CategorySelector.class));
                System.out.println("Category Button Clicked");
            }
        });
        categoryButton.setBackgroundColor(Color.BLUE);
    }

}