package com.example.bloodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInScreen extends AppCompatActivity {

    private EditText signInEmailEditText, signInPasswordEditText;
    private TextView signUpMessage, forgetPassword;
    private Button signInButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);

        mAuth = FirebaseAuth.getInstance();

        signInEmailEditText = findViewById(R.id.signInEmailAddress);
        signInPasswordEditText = findViewById(R.id.signInPassword);
        signUpMessage = findViewById(R.id.signUpMessage);
        signInButton = findViewById(R.id.signInButton);
        progressBar = findViewById(R.id.progressBar);
        forgetPassword = findViewById(R.id.forgetPassMessage);


       signUpMessage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent signUpIntent = new Intent(getApplicationContext(), SignUpScreen.class);
               startActivity(signUpIntent);
           }
       });

       forgetPassword.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent forgetIntent = new Intent(getApplicationContext(), forgetPassword.class);
               startActivity(forgetIntent);
           }
       });

       signInButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               userLogin();
           }
       });

    }

    private void userLogin(){

        String email = signInEmailEditText.getText().toString().trim();
        String password = signInPasswordEditText.getText().toString().trim();


        //checking the validity of the email
        if(email.isEmpty())
        {
            signInEmailEditText.setError("Enter an email address");
            signInEmailEditText.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signInEmailEditText.setError("Enter a valid email address");
            signInEmailEditText.requestFocus();
            return;
        }


        //checking the validity of the password
        if(email.isEmpty())
        {
            signInPasswordEditText.setError("Enter a password");
            signInPasswordEditText.requestFocus();
            return;
        }

        if(password.length()<6){
            signInPasswordEditText.setError("Minimum length of a password should be 6");
            signInPasswordEditText.requestFocus();
            return;
        }


            progressBar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);

                    if(task.isSuccessful()){

                        FirebaseUser user = mAuth.getCurrentUser();

                        if(user.isEmailVerified()){
                            FirebaseAuth.getInstance().signOut();
                            Toast.makeText(SignInScreen.this, "Please verify your email first to sign in!", Toast.LENGTH_SHORT).show();
                        }
                        else{

                            finish();
                            Toast.makeText(getApplicationContext(), "Successfully Login!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Wrong Email or Password!!", Toast.LENGTH_SHORT).show();
                    }

                }
            });


    }


}