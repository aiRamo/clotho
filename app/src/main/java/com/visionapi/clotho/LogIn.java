package com.visionapi.clotho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class LogIn extends AppCompatActivity{

    MaterialButton signUpButton;
    Intent registration, homepage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //TODO: When the login button is clicked, check if both text fields contain 'admin', if they do, redirect the app to Homepage

        homepage = new Intent(LogIn.this, MainActivity.class);

        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.Password);
        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals("admin") && password.getText().toString().
                        equals("admin")){
                    GlobalLoginChecker.isLoggedIn = true;
                    startActivity(homepage);

                }else{
                    Toast.makeText(LogIn.this,"Login Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });




        signUpButton = findViewById(R.id.signup);
        registration = new Intent(LogIn.this, Registration.class);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(registration);
            }
        });

    }
}
