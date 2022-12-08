package com.visionapi.clotho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LogIn extends AppCompatActivity{

    Intent registration, homepage;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://clotho-a9c47-default-rtdb.firebaseio.com/");

    ArrayList<DataSnapshot> savedImages = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        homepage = new Intent(LogIn.this, MainActivity.class);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText Password = (EditText) findViewById(R.id.Password);
        final MaterialButton login = (MaterialButton) findViewById(R.id.loginbtn);
        final MaterialButton signup = (MaterialButton) findViewById(R.id.signup);
        final MaterialButton forgotpass = (MaterialButton) findViewById(R.id.forgotpass);

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
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // check if username and password exist in firebase database
                            if(snapshot.hasChild(userName)){
                                // if the username matches get the password and match it to
                                // the firebase one

                                 final String getPassword = snapshot.child(userName).child("password").getValue(String.class);

                                 if(getPassword.equals(passWord)){
                                     Toast.makeText(LogIn.this, "Successful Log In", Toast.LENGTH_SHORT).show();

                                     // open user account straight to the splash page
                                     GlobalVars.isLoggedIn = true;

                                     GlobalVars.userNameTxt_Global = userName;
                                     GlobalVars.genderTxt_Global = snapshot.child(userName).child("gender").getValue(String.class);
                                     GlobalVars.emailTxt_Global = snapshot.child(userName).child("email").getValue(String.class);
    

                                     //Get data in datasnapshot
                                     for (DataSnapshot dsp : snapshot.child(userName).child("Saved Searches").getChildren()) {
                                         if (GlobalVars.savedCount < 5) {
                                             savedImages.add((dsp));
                                             GlobalVars.savedCount++;
                                         }

                                     }

                                     Toast.makeText(LogIn.this, "AMAZON: " + savedImages.get(1).child("amazonLink").getValue(), Toast.LENGTH_LONG).show();
                                     Toast.makeText(LogIn.this, "URI: " + savedImages.get(0).child("imageuri").getValue(), Toast.LENGTH_LONG).show();
                                     startActivity(new Intent(LogIn.this,MainActivity.class));
                                     finish();
                                 }
                                 else{
                                     Toast.makeText(LogIn.this, "Wrong Password entered", Toast.LENGTH_SHORT).show();
                                 }
                            }
                            else{
                                Toast.makeText(LogIn.this, "No User:" + userName, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
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

        // when forgot password button is clicked
        // will send user to forgot password class page
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // opens Registration Activity
                startActivity(new Intent(LogIn.this,ForgotPassword.class));
                // might need a break
                // break;
            }
        });


    }
}
