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

import com.google.android.material.chip.Chip;

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Shoe Variables
    String[] users = {"Select","1", "2", "3", "4", "5","6","7","8","9","10","11","12","13","14"};
    String[] bottomlist = {"Select","0","2","4","6","8","10","12","14","16","18","20","22","24","26","28","30","32","34","36","38","40"};
    String[] topList = {"Select","Small","Medium","Large","X-Large","XX-Large","XXX-Large"};
    String[] genderList = {"Select","Woman","Man","Unisex"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //New shoe drop down
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //Shoe Drop Down
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
        //---------------
        //Top Drop Down
        Spinner topSpin = (Spinner) findViewById(R.id.topSpin);
        ArrayAdapter<String> topAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, topList);
        topAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        topSpin.setAdapter(topAdapter);
        topSpin.setOnItemSelectedListener(this);
        //-------------
        //Bottom Drop Down
        Spinner botSpin = (Spinner) findViewById(R.id.bottomSpin);
        ArrayAdapter<String> botAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bottomlist);
        botAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        botSpin.setAdapter(botAdapter);
        botSpin.setOnItemSelectedListener(this);
        ////------------
        //Gender Drop Down
        Spinner genderSpin = (Spinner) findViewById(R.id.genderSpin);
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genderList);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpin.setAdapter(genderAdapter);
        genderSpin.setOnItemSelectedListener(this);
        //--------

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(), "Shoe Size: "+ users[adapterView.getSelectedItemPosition()] ,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
