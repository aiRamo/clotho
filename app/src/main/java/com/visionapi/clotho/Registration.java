package com.visionapi.clotho;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // create object of DatabaseReference class to access firebase's Realtime Database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://clotho-a9c47-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        // setting up all the variables for contents of registration page
        final EditText fullname = findViewById(R.id.editTextPersonName);
        final EditText username = findViewById(R.id.editTextUsername);
        final EditText CreatePassword = findViewById(R.id.editTextPassword);
        final EditText ConfirmPassword = findViewById(R.id.editTextPassword2);
        final EditText phonenumber = findViewById(R.id.editTextPhonenumber);
        final EditText email = findViewById(R.id.editTextEmail);


        final Spinner gender = findViewById(R.id.spinner4);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_items,
                R.layout.color_spinner_layout
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        gender.setAdapter(adapter);
        gender.setOnItemSelectedListener(this);




        final Button registerbtn = findViewById(R.id.registerButton);

        // might want to keep the registerbtn to save new account info
        // and create new sign in button to sign in automatically from
        // registration page

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get date from EditTexts into String variables


                final String fullNameTxt = fullname.getText().toString();
                final String userNameTxt = username.getText().toString();
                final String passwordTxt = CreatePassword.getText().toString();
                final String confirmTxt = ConfirmPassword.getText().toString();
                final String genderTxt = gender.getSelectedItem().toString();
                final String phoneTxt = phonenumber.getText().toString();
                final String emailTxt = email.getText().toString();

                //JUST FOR TESTING SAVE FEATURE, DISREGARD LATER...
//
//                GlobalVars.fullNameTxt_Global = fullNameTxt;
//                GlobalVars.lastNameTxt_Global = lastNameTxt;
//                GlobalVars.userNameTxt_Global = userNameTxt;
//                GlobalVars.passwordTxt_Global = passwordTxt;
//                GlobalVars.genderTxt_Global = genderTxt;

                // check if the user fills all the fields before sending data to firebase
                if(fullNameTxt.isEmpty() || passwordTxt.isEmpty()
                        || genderTxt.equals("Gender:") || confirmTxt.isEmpty() || phoneTxt.isEmpty()){
                    Toast.makeText(Registration.this, "Please fill out all fields",Toast.LENGTH_SHORT).show();
                } else if(!passwordTxt.equals(confirmTxt)) {
                    Toast.makeText(Registration.this, "Password fields do not match", Toast.LENGTH_SHORT).show();
                }

                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // check if email is not registered before
                            if(snapshot.hasChild(userNameTxt)){
                                Toast.makeText(Registration.this, "Phone Number is already in use", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                // send data to firebase Realtime Database
                                // we are going to user the phone number as the unique identity of every user
                                // so all the other details of users will be under the email
                                databaseReference.child("users").child(userNameTxt).child("fullName").setValue(fullNameTxt);
                                databaseReference.child("users").child(userNameTxt).child("password").setValue(passwordTxt);
                                databaseReference.child("users").child(userNameTxt).child("gender").setValue(genderTxt);
                                databaseReference.child("users").child(userNameTxt).child("phoneNumber").setValue(phoneTxt);
                                databaseReference.child("users").child(userNameTxt).child("email").setValue(emailTxt);

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

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}