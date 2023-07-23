package com.example.bloodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpScreen extends AppCompatActivity {


    private EditText signUpEmailEditText, signUpPasswordEditText;
    private TextView signInMessage;
    private Button signUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);


        signUpEmailEditText = findViewById(R.id.signUpEmailAddress);
        signUpPasswordEditText = findViewById(R.id.signUpPassword);
        signInMessage = findViewById(R.id.signInMessage);
        signUpButton = findViewById(R.id.signUpButton);


        signInMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = new Intent(getApplicationContext(), SignInScreen.class);
                startActivity(signInIntent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegisterValidityCheck();
            }
        });

    }

    private void userRegisterValidityCheck(){

        String email = signUpEmailEditText.getText().toString().trim();
        String password = signUpPasswordEditText.getText().toString().trim();


        //checking the validity of the email
        if(email.isEmpty())
        {
            signUpEmailEditText.setError("Enter an email address");
            signUpEmailEditText.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signUpEmailEditText.setError("Enter a valid email address");
            signUpEmailEditText.requestFocus();
            return;
        }


        //checking the validity of the password
        if(email.isEmpty())
        {
            signUpPasswordEditText.setError("Enter a password");
            signUpPasswordEditText.requestFocus();
            return;
        }

        if(password.length()<6){
            signUpPasswordEditText.setError("Minimum length of a password should be 6");
            signUpPasswordEditText.requestFocus();
            return;
        }


        Intent intent = new Intent(getApplicationContext(), signUpDetails.class);
        intent.putExtra("email", email);
        intent.putExtra("password",password);
        startActivity(intent);

    }
}