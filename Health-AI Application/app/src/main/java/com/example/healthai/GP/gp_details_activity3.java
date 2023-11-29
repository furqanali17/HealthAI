package com.example.healthai.GP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthai.R;
import com.example.healthai.mainAppActivity.dashboardActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class gp_details_activity3 extends AppCompatActivity {
    Button callGP_Btn;
    String gpMobile, gpFullName, gpSex, gpSpecialties;
    TextView gpNameTextView, gpSexTextView, gpMobileTextView, gpSpecialtiesTextView;
    FloatingActionButton goBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gp_details3);

        gpNameTextView = findViewById(R.id.gp_name);
        gpSexTextView = findViewById(R.id.gp_sex);
        gpMobileTextView = findViewById(R.id.gp_mobile);
        gpSpecialtiesTextView = findViewById(R.id.gpSpecialties);

        getGpInfo();

        callGP_Btn = findViewById(R.id.callGP_Btn);
        callGP_Btn.setOnClickListener(v -> action_dial());

        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(v -> {
            Intent intent = new Intent(gp_details_activity3.this, dashboardActivity.class);
            startActivity(intent);
        });

    }

    private void getGpInfo() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
           String uid = user.getUid();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("Professionals").exists()) {
                        gpMobile = dataSnapshot.child("Professionals").child("mobile").getValue(String.class);
                        gpFullName = dataSnapshot.child("Professionals").child("fullname").getValue(String.class);
                        gpSex = dataSnapshot.child("Professionals").child("sex").getValue(String.class);
                        gpSpecialties = dataSnapshot.child("Professionals").child("specialties").getValue(String.class);

                        if (gpFullName != null && gpSex != null && gpMobile != null && gpSpecialties != null) {
                            // Set GP information in the TextViews
                            gpNameTextView.setText(gpFullName);
                            gpSexTextView.setText(gpSex);
                            gpMobileTextView.setText(gpMobile);
                            gpSpecialtiesTextView.setText(gpSpecialties);
                        } else {
                            Toast.makeText(gp_details_activity3.this, "Error fetching GP information", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle errors here
                    Toast.makeText(gp_details_activity3.this, "Error fetching GP mobile number", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void action_dial(){
        if (gpMobile != null && !gpMobile.isEmpty()) {
            Intent intentDial = new Intent(Intent.ACTION_DIAL);
            Uri number = Uri.parse("tel:" + gpMobile);
            intentDial.setData(number);

            try {
                startActivity(intentDial);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(gp_details_activity3.this, "Error calling GP", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(gp_details_activity3.this, "GP mobile number not available", Toast.LENGTH_SHORT).show();
        }
    }
}