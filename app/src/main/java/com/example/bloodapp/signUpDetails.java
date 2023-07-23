package com.example.bloodapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

import DataModels.userProfileData;

public class signUpDetails extends AppCompatActivity {


    private EditText nameText,phoneNo;
    private AutoCompleteTextView bloodDropdown, divisonDropdown, districtDropdown;
    private TextView datePicker;
    private Button doneButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private String[] bloodList;
    private String[] divisonList;
    private String[] districtList;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    boolean valid = true;
    String divison = "",bloodGroup = "",district = "",name = "",phone = "", date = "",mail = "",password = "",available = "true";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_details);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        if(extras != null){

            mail = extras.getString("email");
            password = extras.getString("password");
        }

        nameText = findViewById(R.id.nameText);
        phoneNo = findViewById(R.id.contact);
        bloodDropdown = findViewById(R.id.BloodGroupDropDown);
        divisonDropdown = findViewById(R.id.DivisonDropDown);
        districtDropdown = findViewById(R.id.DistrictDropDown);
        datePicker = findViewById(R.id.date_picker);
        doneButton = findViewById(R.id.doneButton);
        progressBar = findViewById(R.id.progressBar);


        bloodList = getResources().getStringArray(R.array.blood_group);
        ArrayAdapter<String> arrayAdapter_blood = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, bloodList);

        bloodDropdown.setAdapter(arrayAdapter_blood);

        divisonList = getResources().getStringArray(R.array.divison);
        ArrayAdapter<String> arrayAdapter_divison = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, divisonList);

        divisonDropdown.setAdapter(arrayAdapter_divison);


        bloodDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                bloodGroup = (String)parent.getItemAtPosition(position);
            }
        });


        divisonDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                divison = (String)parent.getItemAtPosition(position);

                if(divison.equals("Madhya Pradesh")){
                    districtList = getResources().getStringArray(R.array.MadhyaPradeshDistrict);
                    ArrayAdapter<String> arrayAdapter_district = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, districtList);

                    districtDropdown.setAdapter(arrayAdapter_district);
                }

//                else if(divison.equals("Chattogram")){
//                    districtList = getResources().getStringArray(R.array.chattogramDistrict);
//                    ArrayAdapter<String> arrayAdapter_district = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, districtList);
//
//                    districtDropdown.setAdapter(arrayAdapter_district);
//                }
//
//                else if(divison.equals("Rajshahi")){
//                    districtList = getResources().getStringArray(R.array.rajshahiDistrict);
//                    ArrayAdapter<String> arrayAdapter_district = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, districtList);
//
//                    districtDropdown.setAdapter(arrayAdapter_district);
//                }
//
//                else if(divison.equals("Barishal")){
//                    districtList = getResources().getStringArray(R.array.barihsalDistrict);
//                    ArrayAdapter<String> arrayAdapter_district = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, districtList);
//
//                    districtDropdown.setAdapter(arrayAdapter_district);
//                }
//
//                else if(divison.equals("Khulna")){
//                    districtList = getResources().getStringArray(R.array.khulnaDistrict);
//                    ArrayAdapter<String> arrayAdapter_district = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, districtList);
//
//                    districtDropdown.setAdapter(arrayAdapter_district);
//                }
//
//                else if(divison.equals("Rangpur")){
//                    districtList = getResources().getStringArray(R.array.rangpurDistrict);
//                    ArrayAdapter<String> arrayAdapter_district = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, districtList);
//
//                    districtDropdown.setAdapter(arrayAdapter_district);
//                }
//
//                else if(divison.equals("Sylhet")){
//                    districtList = getResources().getStringArray(R.array.sylhetDistrict);
//                    ArrayAdapter<String> arrayAdapter_district = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, districtList);
//
//                    districtDropdown.setAdapter(arrayAdapter_district);
//                }
//
//                else if(divison.equals("Mymensingh")){
//                    districtList = getResources().getStringArray(R.array.mymensinghDistrict);
//                    ArrayAdapter<String> arrayAdapter_district = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, districtList);
//
//                    districtDropdown.setAdapter(arrayAdapter_district);
//                }

            }
        });

        districtDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                district = (String)parent.getItemAtPosition(position);
            }
        });


        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(signUpDetails.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {

                month = month +1;
                date = day + "/" + month + "/" + year;
                datePicker.setText(date);

            }
        };


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

    }


    private void saveData(){

        name = nameText.getText().toString();

        phone = phoneNo.getText().toString();

        if(name.equals("")){
            Toast.makeText(this, "Enter Name!", Toast.LENGTH_SHORT).show();
        }

        else if(bloodGroup.equals("")){

            Toast.makeText(this, "Enter Blood Group!", Toast.LENGTH_SHORT).show();

        }

        else if(divison.equals("")){
            Toast.makeText(this, "Enter Divison!", Toast.LENGTH_SHORT).show();
        }

        else if(district.equals("")){
            Toast.makeText(this, "Enter District!", Toast.LENGTH_SHORT).show();
        }

        else if(date.equals("")){
            Toast.makeText(this, "Please select last donation date!!", Toast.LENGTH_SHORT).show();
        }


        else if(phone.equals("")){
            Toast.makeText(this, "Enter Contact Number!", Toast.LENGTH_SHORT).show();
        }

        else if(phone.length() != 11){
            Toast.makeText(this, "Enter Correct Contact Number!", Toast.LENGTH_SHORT).show();
        }

        else{

            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser rUser = mAuth.getCurrentUser();

                                rUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(signUpDetails.this, "Verification email has been sent!", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(signUpDetails.this, "Verification email can't be sent!", Toast.LENGTH_SHORT).show();
                                    }
                                });


                                String userId = rUser.getUid();
                                databaseReference = FirebaseDatabase.getInstance("").getReference("users").child(userId);

                                HashMap<String, String> hashMap = new HashMap<>();

                                hashMap.put("uid",userId);
                                hashMap.put("name",name);
                                hashMap.put("bloodGroup",bloodGroup);
                                hashMap.put("divison",divison);
                                hashMap.put("district",district);
                                hashMap.put("phone",phone);
                                hashMap.put("date",date);
                                hashMap.put("availability",available);

                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressBar.setVisibility(View.GONE);

                                        if(task.isSuccessful()){
                                            userProfileData userProfileData = new userProfileData(userId, name, bloodGroup, divison, district, phone, date, available);
                                            Toast.makeText(getApplicationContext(), "Sign Up successful!!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), SignInScreen.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(),"Database Error!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                progressBar.setVisibility(View.GONE);
                                if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(getApplicationContext(),"User is already registered!", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });

        }

    }



}