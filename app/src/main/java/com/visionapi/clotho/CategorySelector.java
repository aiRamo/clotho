package com.visionapi.clotho;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public class CategorySelector extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final int PERMISSION_CODE = 1000;
    Button TakePhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category_selector);

        TakePhoto = findViewById(R.id.btn_takePhoto);
        Intent kotlinPreview = new Intent(CategorySelector.this, ProductSearchAPIClient.class);


        //Checks to see if pants button is clicked and will than show the take photo button
        Button b = (Button) findViewById(R.id.btn_takePhoto);
        b.setVisibility(View.VISIBLE);

        final Spinner pantsbutton = (Spinner) findViewById(R.id.colorSpinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.colorList,
                R.layout.color_spinner_layout
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        pantsbutton.setAdapter(adapter);
        pantsbutton.setOnItemSelectedListener(this);


        TakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pantsbutton.getSelectedItem().toString().equals("Select Color")){
                    Toast.makeText(CategorySelector.this, "Pick an item color!", Toast.LENGTH_SHORT).show();
                } else {
                    GlobalVars.itemColor = pantsbutton.getSelectedItem().toString();
                    startActivity(kotlinPreview);
                }



            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String colorText = adapterView.getSelectedItem().toString();
        CheckedTextView spinnerStyle = findViewById(R.id.spinnerStyle);
        Toast.makeText(this, colorText, Toast.LENGTH_SHORT).show();
        ConstraintLayout root=(ConstraintLayout) findViewById(R.id.categoryStuff);
        spinnerStyle.setTypeface(Typeface.DEFAULT_BOLD);
        if (colorText.equals("Select Color")) {
            spinnerStyle.setTextColor(Color.parseColor("#000000"));
            adapterView.setBackgroundColor(Color.parseColor("#F2F3F4"));
        }
        if (colorText.equals("Blue")) {
            spinnerStyle.setTextColor(Color.parseColor("#F2F3F4"));
            adapterView.setBackgroundColor(Color.parseColor("#1663BE"));
        }
        if (colorText.equals("White")) {
            spinnerStyle.setTextColor(Color.parseColor("#000000"));
            adapterView.setBackgroundColor(Color.parseColor("#F2F3F4"));
        }
        if (colorText.equals("Black")) {
            spinnerStyle.setTextColor(Color.parseColor("#F2F3F4"));
            adapterView.setBackgroundColor(Color.parseColor("#000000"));
        }
        if (colorText.equals("Brown")) {
            spinnerStyle.setTextColor(Color.parseColor("#F2F3F4"));
            adapterView.setBackgroundColor(Color.parseColor("#964B00"));
        }
        if (colorText.equals("Grey")) {
            spinnerStyle.setTextColor(Color.parseColor("#F2F3F4"));
            adapterView.setBackgroundColor(Color.parseColor("#808080"));
        }
        if (colorText.equals("Red")) {
            spinnerStyle.setTextColor(Color.parseColor("#F2F3F4"));
            adapterView.setBackgroundColor(Color.parseColor("#FF0000"));
        }
        if (colorText.equals("Orange")) {
            spinnerStyle.setTextColor(Color.parseColor("#F2F3F4"));
            adapterView.setBackgroundColor(Color.parseColor("#FFA500"));
        }
        if (colorText.equals("Yellow")) {
            spinnerStyle.setTextColor(Color.parseColor("#000000"));
            adapterView.setBackgroundColor(Color.parseColor("#FFFF00"));
        }
        if (colorText.equals("Green")) {
            spinnerStyle.setTextColor(Color.parseColor("#000000"));
            adapterView.setBackgroundColor(Color.parseColor("#00FF00"));
        }
        if (colorText.equals("Purple")) {
            spinnerStyle.setTextColor(Color.parseColor("#F2F3F4"));
            adapterView.setBackgroundColor(Color.parseColor("#A020F0"));
        }
        if (colorText.equals("Pink")) {
            spinnerStyle.setTextColor(Color.parseColor("#F2F3F4"));
            adapterView.setBackgroundColor(Color.parseColor("#FFC0CB"));
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}

