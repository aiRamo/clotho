package com.visionapi.clotho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Shoe Variables
    String[] users = {"1", "2", "3", "4", "5","6","7","8","9","10","11","12","13","14"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //New shoe drop down
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Spinner spin = (Spinner) findViewById(R.id.dropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);


        ImageButton settingsButton = (ImageButton) findViewById(R.id.imageButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, MainActivity.class));
                System.out.println("Settings Button Clicked");
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(), "Shoe Size: "+ users[adapterView.getSelectedItemPosition()] ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
