package com.visionapi.clotho;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;


public class CategorySelector extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final int PERMISSION_CODE = 1000;
    Button TakePhoto;
    Uri image_uri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selector);

        TakePhoto = findViewById(R.id.btn_takePhoto);
        Intent kotlinPreview = new Intent(CategorySelector.this, ProductSearchAPIClient.class);


        TakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(kotlinPreview);


            }
        });
        //Checks to see if pants button is clicked and will than show the take photo button
        Button b = (Button) findViewById(R.id.btn_takePhoto);
        Spinner pantsbutton = (Spinner) findViewById(R.id.colorSpinner);
        pantsbutton.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                b.setVisibility(View.VISIBLE);
                System.out.println("Color Selector Clicked");
            }
        });

        /// Color Drop down
        String[] colorList = {"1", "2", "3", "4", "5","6","7","8","9","10","11","12","13","14"};
        setContentView(R.layout.activity_category_selector);
        Spinner spin = (Spinner) findViewById(R.id.colorSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colorList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

