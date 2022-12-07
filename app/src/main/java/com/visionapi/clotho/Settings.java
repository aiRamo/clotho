package com.visionapi.clotho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    //Shoe Variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        //New shoe drop down
        final Spinner dropdowns = findViewById(R.id.dropdown);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_items,
                R.layout.color_spinner_layout
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        dropdowns.setAdapter(adapter);


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