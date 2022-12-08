package com.visionapi.clotho;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.visionapi.clotho.databinding.ActivityMainBinding;


public class CategorySelector extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final int PERMISSION_CODE = 1000;
    Button TakePhoto;
    Uri image_uri;

    ColorObject selectedColor;
    ActivityMainBinding binding;


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
        b.setVisibility(View.VISIBLE);
        /*Spinner pantsbutton = (Spinner) findViewById(R.id.colorSpinner);
        pantsbutton.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                b.setVisibility(View.VISIBLE);
                System.out.println("Color Selector Clicked");
            }
        });*/
        final Spinner pantsbutton = (Spinner) findViewById(R.id.colorSpinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.colorList,
                R.layout.color_spinner_layout
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        pantsbutton.setAdapter(adapter);
        pantsbutton.setOnItemSelectedListener(this);


        /// Color Drop down
        /*String[] colorList = {"1", "2", "3", "4", "5","6","7","8","9","10","11","12","13","14"};
        setContentView(R.layout.activity_category_selector);
        Spinner spin = (Spinner) findViewById(R.id.colorSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colorList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);*/


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String colorText = adapterView.getSelectedItem().toString();
        Toast.makeText(this, colorText, Toast.LENGTH_SHORT).show();
        ConstraintLayout root=(ConstraintLayout) findViewById(R.id.categoryStuff);
        if (colorText.equals("Blue")) {
            root.setBackgroundColor(Color.parseColor("#6987C9"));
            Toast.makeText(this, "Blue Shit", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}

