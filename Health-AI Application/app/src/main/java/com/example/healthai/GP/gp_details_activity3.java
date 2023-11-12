package com.example.healthai.GP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthai.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class gp_details_activity3 extends AppCompatActivity {
    Button callGP_Btn;
    String gpMobile, gpName, gpSex;
    TextView gpInfoTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gp_details3);

        getGpInfo();

        callGP_Btn = findViewById(R.id.callGP_Btn);
        gpInfoTextView = findViewById(R.id.gp_info_textView);
        callGP_Btn.setOnClickListener(v -> action_dial());
    }

    private void getGpInfo() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            String email = user.getEmail();
            if (email != null) {
                String userKey = email.replace('.', ',');

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userKey);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("gp").exists()) {
                            gpMobile = dataSnapshot.child("gp").child("mobile").getValue(String.class);
                            gpName = dataSnapshot.child("gp").child("name").getValue(String.class);
                            gpSex = dataSnapshot.child("gp").child("sex").getValue(String.class);

                            if (gpName != null && gpSex != null && gpMobile != null) {
                                String gpInfo = "Name: " + gpName + "\nSex: " + gpSex + "\nPhone: " + gpMobile;
                                gpInfoTextView.setText(gpInfo);
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