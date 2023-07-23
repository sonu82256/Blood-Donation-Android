package com.example.bloodapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Adapters.showUsersAdapter;
import DataModels.userProfileData;

public class allDonorList extends AppCompatActivity {


    private ListView listView;
    private List<userProfileData> usersList;
    private showUsersAdapter showusersAdapter;
    private DatabaseReference databaseReference;
    private Calendar calendar;
    private Date donateDate;
    private String lastDonateDate;
    private String todaysDate;
    private Toolbar menuItem;
    private TextView noDataText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_request);


        databaseReference = FirebaseDatabase.getInstance("https://aust-blood-donation-app-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users");

        usersList = new ArrayList<>();
        showusersAdapter = new showUsersAdapter(allDonorList.this, usersList);

        listView= findViewById(R.id.listView);

        calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        donateDate = calendar.getTime();

        todaysDate = sdf.format(donateDate);

        menuItem = findViewById(R.id.toolbar);

        noDataText = findViewById(R.id.noText);


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
//
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
                    String details = "Download AUST Blood Donation App: https://drive.google.com/drive/folders/1lhIfbIdxVcELHFWTv_fxtVqB61D3ULkO?usp=sharing";
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

    }


    @Override
    protected void onStart() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                usersList.clear();
                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    userProfileData userProfileData = dataSnapshot1.getValue(DataModels.userProfileData.class);

                    lastDonateDate = userProfileData.getDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date startDate = sdf.parse(lastDonateDate);
                        Date endDate = sdf.parse(todaysDate);
                        long diff = (endDate.getTime() - startDate.getTime())/(24*60*60*1000);
                        int d = (int)diff;

                        if(userProfileData.getAvailability().equals("true") && d > 120)
                            usersList.add(userProfileData);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                if(usersList.isEmpty()){
                    listView.setVisibility(View.GONE);
                    noDataText.setVisibility(View.VISIBLE);
                }


                listView.setAdapter(showusersAdapter);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        view.setSelected(true);
                        String phone = showusersAdapter.getItem(position).getPhone();
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        String phoneNo = "tel:" + phone;
                        intent.setData(Uri.parse(phoneNo));
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(allDonorList.this, "Can't Read Data! Check internet connection.", Toast.LENGTH_SHORT).show();
            }
        });

        super.onStart();
    }


}