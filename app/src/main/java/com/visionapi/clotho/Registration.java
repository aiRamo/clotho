package com.visionapi.clotho;

import static com.google.firebase.database.FirebaseDatabase.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registration extends AppCompatActivity {
    // create object of DatabaseReference class to access firebase's Realtime Database
    DatabaseReference databaseReference = getInstance().getReferenceFromUrl("https://clotho-a9c47-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        // setting up all the variables for contents of registration page
        final EditText fullname = findViewById(R.id.editTextTextPersonName);
        final EditText lastname = findViewById(R.id.editTextTextPersonName2);
        final EditText email = findViewById(R.id.editTextTextEmailAddress);
        final EditText CreatePassword = findViewById(R.id.editTextTextPassword);
        final EditText gender = findViewById(R.id.editTextTextPersonName3);


        final Button registerbtn = findViewById(R.id.registerButton);

        // might want to keep the registerbtn to save new account info
        // and create new sign in button to sign in automatically from
        // registration page

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get date from EditTexts into String variables


                final String fullNameTxt = fullname.getText().toString();
                final String lastNameTxt = lastname.getText().toString();
                final String emailTxt = email.getText().toString();
                final String passwordTxt = CreatePassword.getText().toString();
                final String genderTxt = gender.getText().toString();

                // check if the user fills all the fields before sending data to firebase
                if(fullNameTxt.isEmpty() || lastNameTxt.isEmpty() || emailTxt.isEmpty() || passwordTxt.isEmpty()
                        || genderTxt.isEmpty()){
                    Toast.makeText(Registration.this, "Please fill out all fields",Toast.LENGTH_SHORT).show();
                }

                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // check if email is not registered before
                            if(snapshot.hasChild(emailTxt)){
                                Toast.makeText(Registration.this, "Email is already in use", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                // send data to firebase Realtime Database
                                // we are going to user the email address as the unique identity of every user
                                // so all the other details of users will be under the email
                                databaseReference.child("users").child(emailTxt).child("fullName").setValue(fullNameTxt);
                                databaseReference.child("users").child(emailTxt).child("lastName").setValue(lastNameTxt);
                                databaseReference.child("users").child(emailTxt).child("password").setValue(passwordTxt);
                                databaseReference.child("users").child(emailTxt).child("gender").setValue(genderTxt);

                                // show a registration was successful message to the user and finish the activity
                                Toast.makeText(Registration.this,"User has been registered successfully",Toast.LENGTH_SHORT).show();

                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

            }
        });


        // signUpButton.setOnClickListener(new View.OnClickListener() {
          //  @Override
          //  public void onClick(View view) {
           //     finish();
           // }
        //});

        // when we click the register button we will be directed
        // to our new user splash page
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Use Intent to move from one activity page to another
                Intent intent = new Intent(Registration.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }


}