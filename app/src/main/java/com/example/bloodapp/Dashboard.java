package com.example.bloodapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    private CardView allRequestButton, makeRequestButton, allDonorButton, searchDonorButton;

    private Toolbar menuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        allRequestButton = findViewById(R.id.all_blood_request);
        makeRequestButton = findViewById(R.id.make_request);
        allDonorButton = findViewById(R.id.all_donor);
        searchDonorButton = findViewById(R.id.search_donor);
        menuItem = findViewById(R.id.toolbar);


        allRequestButton.setOnClickListener(this);
        makeRequestButton.setOnClickListener(this);
        allDonorButton.setOnClickListener(this);
        searchDonorButton.setOnClickListener(this);


        menuItem.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId() == R.id.my_profile){
                    Intent intent = new Intent(getApplicationContext(), userProfile.class);
                    startActivity(intent);
                    return true;
                }
//
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
                    intent.setData(Uri.parse("mailto:sonu82256@gmail.com")); // only email apps should handle this
                    intent.putExtra(Intent.EXTRA_EMAIL, "sonu82256@gmail.com");
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
    public void onClick(View v) {

        if (v.getId() == R.id.all_blood_request) {

            Intent intent = new Intent(getApplicationContext(), showRequest.class);
            startActivity(intent);

        }

        if (v.getId() == R.id.make_request) {

            Intent intent = new Intent(getApplicationContext(), MakeRequestActivity.class);
            startActivity(intent);

        }

        if (v.getId() == R.id.all_donor) {

            Intent intent = new Intent(getApplicationContext(), allDonorList.class);
            startActivity(intent);

        }

        if (v.getId() == R.id.search_donor) {

            Intent intent = new Intent(getApplicationContext(), SearchPage.class);
            startActivity(intent);

        }
    }

}