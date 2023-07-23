package com.example.bloodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import DataModels.bloodPostData;

public class MakeRequestActivity extends AppCompatActivity {

    private EditText nameText,requestMessageText, detailAddress, phoneNo;
    private Button request_submit_button;
    private AutoCompleteTextView bloodDropdown, divisonDropdown, districtDropdown;
    private String[] bloodList;
    private String[] divisonList;
    private String[] districtList;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    boolean valid = true;
    private String divison = "",bloodGroup = "",district = "",address = "",message = "",name = "",phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_request);


        nameText = findViewById(R.id.nameText);
        requestMessageText = findViewById(R.id.requestMessage);
        request_submit_button = findViewById(R.id.submit_request_button);
        bloodDropdown = findViewById(R.id.BloodGroupDropDown);
        divisonDropdown = findViewById(R.id.DivisonDropDown);
        districtDropdown = findViewById(R.id.DistrictDropDown);
        detailAddress = findViewById(R.id.detailAdress);
        phoneNo = findViewById(R.id.contactNumber);

        databaseReference = FirebaseDatabase.getInstance("https://aust-blood-donation-app-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("bloodPost");


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



        request_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    saveData();
            }
        });
    }



    private void saveData(){

        name = nameText.getText().toString();

        address = detailAddress.getText().toString();

        message = requestMessageText.getText().toString();

        phone = phoneNo.getText().toString();

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

        else if(address.equals("")){
            detailAddress.requestFocus();
            Toast.makeText(this, "Enter Address!", Toast.LENGTH_SHORT).show();
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
            String key = databaseReference.push().getKey();

            FirebaseUser rUser = mAuth.getCurrentUser();
            String userId = rUser.getUid();

            bloodPostData bloodPostData = new bloodPostData(key, userId, name, bloodGroup, divison, district, address, phone, message, 0);

            databaseReference.child(key).setValue(bloodPostData);

            Toast.makeText(this, "Post Updated!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(intent);
            finish();

        }

    }

}