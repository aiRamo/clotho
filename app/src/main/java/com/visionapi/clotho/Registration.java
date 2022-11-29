package com.visionapi.clotho;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        final EditText Firstname = findViewById(R.id.editTextTextPersonName);
        final EditText Lastname = findViewById(R.id.editTextTextPersonName2);
        final EditText email_address = findViewById(R.id.editTextTextEmailAddress);
        final EditText Password = findViewById(R.id.editTextTextPassword);
        final EditText Gender = findViewById(R.id.editTextTextPersonName3);


        final Button registerButton = findViewById(R.id.registerButton);

    }

}