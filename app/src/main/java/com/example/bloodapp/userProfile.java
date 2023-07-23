package com.example.bloodapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import DataModels.userProfileData;

public class userProfile extends AppCompatActivity {

    private TextView datePicker, name, bloodGroup, divison, district, phone, date;
    private SwitchCompat available;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Button editButton;
    private String Name = "", BloodGroup = "", Divison = "", District = "", Phone = "", Date = "", Available = "";
    private Toolbar menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name = findViewById(R.id.name);
        bloodGroup = findViewById(R.id.bloodGroup);
        divison = findViewById(R.id.divison);
        district = findViewById(R.id.district);
        phone = findViewById(R.id.phone);
        date = findViewById(R.id.date_picker);
        available = findViewById(R.id.available);
        editButton = findViewById(R.id.edit_profile_button);
        menuItem = findViewById(R.id.toolbar);


        menuItem.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId() == R.id.my_profile){
                    Intent intent = new Intent(getApplicationContext(), userProfile.class);
                    startActivity(intent);
                    return true;
                }

//                if(item.getItemId() == R.id.my_request){
//                    Intent intent = new Intent(getApplicationContext(), myRequest.class);
//                    startActivity(intent);
//                    return true;
//                }

//                if(item.getItemId() == R.id.about_us){
//                    Intent intent = new Intent(getApplicationContext(), aboutUs.class);
//                    startActivity(intent);
//                    return true;
//                }

                if(item.getItemId() == R.id.logout){
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getApplicationContext(), SignInScreen.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finishAffinity();
                    return true;
                }


                if(item.getItemId() == R.id.share){
                    String details = "Download Blood Donation App: https://drive.google.com/drive/folders/1lhIfbIdxVcELHFWTv_fxtVqB61D3ULkO?usp=sharing";
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, details);
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                    return true;
                }

                if(item.getItemId() == R.id.report){
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:austblooddonationapp@gmail.com")); // only email apps should handle this
                    intent.putExtra(Intent.EXTRA_EMAIL, "austblooddonationapp@gmail.com");
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                    return true;
                }

                return false;

            }
        });


        available.setClickable(false);

        FirebaseUser rUser = mAuth.getCurrentUser();
        String userId = rUser.getUid();

        databaseReference = FirebaseDatabase.getInstance("https://aust-blood-donation-app-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                userProfileData userProfileData = snapshot.getValue(DataModels.userProfileData.class);


                Name = userProfileData.getName();
                BloodGroup = userProfileData.getBloodGroup();
                Divison = userProfileData.getDivison();
                District = userProfileData.getDistrict();
                Phone = userProfileData.getPhone();
                Date = userProfileData.getDate();
                Available = userProfileData.getAvailability();

                name.setText("Name: "+Name);
                bloodGroup.setText("Blood Group: "+BloodGroup);
                divison.setText("Divison: "+Divison);
                district.setText("District: "+District);
                phone.setText("Contact No: "+Phone);
                date.setText(Date);
                if(Available.equals("true"))
                    available.setChecked(true);
                else
                    available.setChecked(false);

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(userProfile.this, "Can't Read Data! Check internet connection.", Toast.LENGTH_SHORT).show();
            }
        });


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), editProfile.class);
                intent.putExtra("name", Name);
                intent.putExtra("blood", BloodGroup);
                intent.putExtra("divison", Divison);
                intent.putExtra("district", District);
                intent.putExtra("phone", Phone);
                intent.putExtra("date", Date);
                intent.putExtra("available", Available);

                startActivity(intent);
                finish();

            }
        });


    }
}