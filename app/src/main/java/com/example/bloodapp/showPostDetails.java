package com.example.bloodapp;

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
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import DataModels.bloodPostData;

public class showPostDetails extends AppCompatActivity {

    private String id, phone, name = "", details;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private TextView showName, showBloodGroup, showDivison, showDistrict, showAddress, showContact, showMessage;
    private Button callButton, deleteButton, shareButton;
    private Toolbar menuItem;
    private int views;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post_details);

        FirebaseUser rUser = mAuth.getCurrentUser();
        String userId = rUser.getUid();


        showName = findViewById(R.id.showName);
        showBloodGroup = findViewById(R.id.showBloodGroup);
        showDivison = findViewById(R.id.showDivison);
        showDistrict = findViewById(R.id.showDistrict);
        showAddress = findViewById(R.id.showAddress);
        showContact = findViewById(R.id.showContact);
        showMessage = findViewById(R.id.showMessage);
        callButton = findViewById(R.id.callButton);
        deleteButton = findViewById(R.id.deleteButton);
        shareButton = findViewById(R.id.shareButton);
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



        Bundle extra = getIntent().getExtras();
        if(extra != null){
            id = extra.getString("postID");
            views = extra.getInt("views");
        }

        databaseReference = FirebaseDatabase.getInstance("https://aust-blood-donation-app-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("bloodPost").child(id);
        databaseReference.child("views").setValue(views);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.getChildrenCount()!=0){

                    bloodPostData showPost = snapshot.getValue(bloodPostData.class);


                    showName.setText("Name: "+ showPost.getName());
                    showBloodGroup.setText("Blood Group: "+ showPost.getBloodGroup());
                    showDivison.setText("Divison: "+ showPost.getDivison());
                    showDistrict.setText("District: "+ showPost.getDistrict());
                    showAddress.setText("Full Address: " + showPost.getAddress());
                    showContact.setText("Contact No: "+ showPost.getPhone());
                    showMessage.setText("Message: "+ showPost.getMessage());
                    phone = showPost.getPhone();


                    details = "----- Blood Needed -----" + "\n" + "Name: "+ showPost.getName() +"\n" + "Blood Group: "+ showPost.getBloodGroup() + "\n" + "Address: " + showPost.getAddress() + " ("
                            + showPost.getDistrict() + ", " + showPost.getDivison() + ")" + "\n" + "Contact: " + showPost.getPhone() + "\n\n" + "#AUST_BLOOD_DONATION_APP";

                    if(showPost.getUid().equals(userId)){
                        deleteButton.setVisibility(View.VISIBLE);
                        callButton.setVisibility(View.GONE);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(showPostDetails.this, "Can't Read Data! Check internet connection.", Toast.LENGTH_SHORT).show();
            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                String phoneNo = "tel:" + phone;
                intent.setData(Uri.parse(phoneNo));
                startActivity(intent);

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.removeValue();
                Toast.makeText(showPostDetails.this, "Post Deleted!", Toast.LENGTH_SHORT).show();
                Intent intent  = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, details);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

    }


}