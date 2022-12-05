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

    Intent registration, homepage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        homepage = new Intent(LogIn.this, MainActivity.class);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText Password = (EditText) findViewById(R.id.Password);
        final MaterialButton login = (MaterialButton) findViewById(R.id.loginbtn);
        final MaterialButton signup = (MaterialButton) findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // when buttons are clicked get the username and password
                // string from user
                final String userName = username.getText().toString();
                final String passWord = Password.getText().toString();

                // if username or password is empty
                // tell user to enter both parts
                if(userName.isEmpty() || passWord.isEmpty()){
                    Toast.makeText(LogIn.this, "Please enter your username or password",Toast.LENGTH_SHORT).show();
                }
                else{
                    // will come back to later in tutorial
                }

                if (userName.equals("admin") && passWord.equals("admin")){
                    GlobalLoginChecker.isLoggedIn=true;
                    startActivity(homepage);
                }
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // opens Registration Activity
                startActivity(new Intent(LogIn.this,Registration.class));
            }
        });


    }
}
