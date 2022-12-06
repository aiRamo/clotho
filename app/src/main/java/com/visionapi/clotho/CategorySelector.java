package com.visionapi.clotho;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;


public class CategorySelector extends AppCompatActivity {

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
        ToggleButton pantsbutton = (ToggleButton) findViewById(R.id.Pants);
        pantsbutton.setOnClickListener( new View.OnClickListener()  {
            public void onClick(View v) {
                b.setVisibility(View.VISIBLE);
                System.out.println("Pants Selector Clicked");
            }
        });


    }

}

