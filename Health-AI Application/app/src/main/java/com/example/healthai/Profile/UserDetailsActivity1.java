package com.example.healthai.Profile;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.healthai.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDetailsActivity1 extends AppCompatActivity {
    FloatingActionButton editFab;
    TextView userDetailsTextView ;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference userReference;
    FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details1);

        editFab = findViewById(R.id.editFab);
        userDetailsTextView  = findViewById(R.id.userDetailsTextView);

        editFab.setOnClickListener(v -> {
            Intent send = new Intent(UserDetailsActivity1.this, UserDetailsActivity2.class);
            startActivity(send);
        });

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        String email = user.getEmail().replace('.', ',');

        userReference = FirebaseDatabase.getInstance().getReference("Users").child(email);

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get the UserDetails object and use the values to update the TextView
                UserDetails userDetails = dataSnapshot.getValue(UserDetails.class);
                if (userDetails != null) {
                    String userDetailsText = "Name: " + userDetails.getName() + "\n" +
                            "Age: " + userDetails.getAge() + "\n" +
                            "Sex: " + userDetails.getSex() + "\n" +
                            "Mobile: " + userDetails.getMobile();
                    userDetailsTextView.setText(userDetailsText);
                }
            }
            public void onCancelled(DatabaseError databaseError) {
                // Getting UserDetails failed, log a message
                Log.w(TAG, "loadUserDetails:onCancelled", databaseError.toException());
            }
        });

    }
}