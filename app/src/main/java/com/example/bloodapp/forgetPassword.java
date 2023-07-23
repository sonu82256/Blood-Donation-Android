package com.example.bloodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class forgetPassword extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailText;
    private Button sendEmailButton;
    private ProgressBar progressBar;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.forgetEmailAddress);
        sendEmailButton = findViewById(R.id.sendEmailButton);
        progressBar = findViewById(R.id.progressBar);



        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailText.getText().toString().trim();

                if(email.isEmpty())
                {
                    emailText.setError("Enter an email address");
                    emailText.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                mAuth.fetchSignInMethodsForEmail(emailText.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        if(task.getResult().getSignInMethods().isEmpty()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(forgetPassword.this, "Please enter correct email!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            mAuth.sendPasswordResetEmail(emailText.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);

                                    if(task.isSuccessful()){
                                        Toast.makeText(forgetPassword.this, "An email has been sent to your email!", Toast.LENGTH_SHORT).show();
                                        Intent signInIntent = new Intent(getApplicationContext(), SignInScreen.class);
                                        startActivity(signInIntent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(forgetPassword.this, "Email can't be sent!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                    }
                });

            }
        });

    }
}