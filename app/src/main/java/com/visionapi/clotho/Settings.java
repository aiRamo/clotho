package com.visionapi.clotho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    private Spinner spinnertextsize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //shoe size drop down
        spinnertextsize = findViewById(R.id.dropdown);
        String[] shoeSizes = getResources().getStringArray(R.array.shoe_sizes);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, shoeSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertextsize.setAdapter(adapter);

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