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

        homepage = new Intent(LogIn.this, MainActivity.class);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.Password);
        final MaterialButton login = (MaterialButton) findViewById(R.id.loginbtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userName = username.getText().toString();
                final String passWord = password.getText().toString();


                if(userName.isEmpty() || passWord.isEmpty()){
                    Toast.makeText(LogIn.this, "Please enter your username or password",Toast.LENGTH_SHORT).show();
                }
                else{

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

    // Need RegisterButton code but keeps showing error


    }
}
