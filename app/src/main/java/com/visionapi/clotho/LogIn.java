package com.visionapi.clotho;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

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
