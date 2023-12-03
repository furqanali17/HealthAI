package com.example.healthai.Profile;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
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
    FirebaseAuth auth;
    CardView user_cardView;
    TextView nameTextView, ageTextView, sexTextView, mobileTextView, emailTextView;
    FirebaseUser user;
    DatabaseReference userReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details1);

        editFab = findViewById(R.id.editFab);
        user_cardView = findViewById(R.id.user_cardView);
        nameTextView = findViewById(R.id.nameTextView);
        ageTextView = findViewById(R.id.ageTextView);
        sexTextView = findViewById(R.id.sexTextView);
        mobileTextView = findViewById(R.id.mobileTextView);
        emailTextView = findViewById(R.id.emailTextView);

        editFab.setOnClickListener(v -> {
            Intent send = new Intent(UserDetailsActivity1.this, UserDetailsActivity2.class);
            startActivity(send);
        });

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        assert user != null;
        String uid = user.getUid();

        userReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);

        userReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get the UserDetails object and use the values to update the TextView
                UserDetails userDetails = dataSnapshot.getValue(UserDetails.class);
                if (userDetails != null) {
                    nameTextView.setText(userDetails.getName());
                    ageTextView.setText(String.valueOf(userDetails.getAge()));
                    sexTextView.setText(userDetails.getSex());
                    mobileTextView.setText(userDetails.getMobile());
                    emailTextView.setText(userDetails.getEmail());
                }
            }
            public void onCancelled(DatabaseError databaseError) {
                // Getting UserDetails failed, log a message
                Log.w(TAG, "loadUserDetails:onCancelled", databaseError.toException());
            }
        });

    }
}