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
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

import DataModels.userProfileData;

public class editProfile extends AppCompatActivity {

    private EditText nameText,phoneNo;
    private AutoCompleteTextView bloodDropdown, divisonDropdown, districtDropdown;
    private TextView datePicker;
    private Button saveButton;
    private SwitchCompat available;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String name = "", bloodGroup = "", divison = "", district = "", phone = "", date = "", availability = "", userId = "";

    private String[] bloodList;
    private String[] divisonList;
    private String[] districtList;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    boolean valid = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        FirebaseUser rUser = mAuth.getCurrentUser();
        userId = rUser.getUid();

        nameText = findViewById(R.id.nameText);
        phoneNo = findViewById(R.id.contact);
        bloodDropdown = findViewById(R.id.BloodGroupDropDown);
        divisonDropdown = findViewById(R.id.DivisonDropDown);
        districtDropdown = findViewById(R.id.DistrictDropDown);
        datePicker = findViewById(R.id.date_picker);
        saveButton = findViewById(R.id.saveButton);
        available = findViewById(R.id.available);
        progressBar = findViewById(R.id.progressBar);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null){

            name = extras.getString("name");
            bloodGroup = extras.getString("blood");
            divison = extras.getString("divison");
            district = extras.getString("district");
            phone = extras.getString("phone");
            date = extras.getString("date");
            availability = extras.getString("available");
        }

        nameText.setText(name);
        bloodDropdown.setText(bloodGroup);
        divisonDropdown.setText(divison);
        districtDropdown.setText(district);
        phoneNo.setText(phone);
        datePicker.setText(date);

        if(availability.equals("true"))
            available.setChecked(true);
        else
            available.setChecked(false);


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

                DatePickerDialog dialog = new DatePickerDialog(editProfile.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);

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


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

    }

    private void saveData(){

        name = nameText.getText().toString();

        phone = phoneNo.getText().toString();

        if(available.isChecked())
            availability = "true";
        else
            availability = "false";


        if(name.equals("")){
            nameText.requestFocus();
            Toast.makeText(this, "Enter Name!", Toast.LENGTH_SHORT).show();
        }

        else if(bloodGroup.equals("")){

            bloodDropdown.requestFocus();
            Toast.makeText(this, "Enter Blood Group!", Toast.LENGTH_SHORT).show();

        }

        else if(divison.equals("")){

            divisonDropdown.requestFocus();
            Toast.makeText(this, "Enter Divison!", Toast.LENGTH_SHORT).show();
        }

        else if(district.equals("")){
            districtDropdown.requestFocus();
            Toast.makeText(this, "Enter District!", Toast.LENGTH_SHORT).show();
        }

        else if(date.equals("")){
            datePicker.requestFocus();
            Toast.makeText(this, "Please select last donation date!!", Toast.LENGTH_SHORT).show();
        }


        else if(phone.equals("")){
            phoneNo.requestFocus();
            Toast.makeText(this, "Enter Contact Number!", Toast.LENGTH_SHORT).show();
        }

        else if(phone.length() != 11){
            phoneNo.requestFocus();
            Toast.makeText(this, "Enter Correct Contact Number!", Toast.LENGTH_SHORT).show();
        }

        else{

            progressBar.setVisibility(View.VISIBLE);

            databaseReference = FirebaseDatabase.getInstance("https://aust-blood-donation-app-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users").child(userId);

            HashMap<String, String> hashMap = new HashMap<>();

            hashMap.put("uid",userId);
            hashMap.put("name",name);
            hashMap.put("bloodGroup",bloodGroup);
            hashMap.put("divison",divison);
            hashMap.put("district",district);
            hashMap.put("phone",phone);
            hashMap.put("date",date);
            hashMap.put("availability",availability);

            databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    progressBar.setVisibility(View.GONE);

                    if(task.isSuccessful()){
                        userProfileData userProfileData = new userProfileData(userId, name, bloodGroup, divison, district, phone, date, availability);
                        Toast.makeText(getApplicationContext(), "Profile Updated successful!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), userProfile.class);
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

        }

    }


}