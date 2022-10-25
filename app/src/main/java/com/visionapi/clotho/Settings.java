package com.visionapi.clotho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageButton settingsButton = (ImageButton)findViewById(R.id.imageButton);
        settingsButton.setOnClickListener( new View.OnClickListener()  {
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, MainActivity.class));
                System.out.println("Settings Button Clicked");
            }
        });
    }
}