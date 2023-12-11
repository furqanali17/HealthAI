package com.example.healthai.mainAppActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthai.Profile.UserDetailsActivity2;
import com.example.healthai.R;
import com.example.healthai.docBotActivity.docBotActivity;
import com.example.healthai.loginActivity.login;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class dashboardActivity extends AppCompatActivity implements View.OnClickListener{
    public CardView predictionCard, fitnessCard, gpCard, userCard;
    FloatingActionButton fab_logout;
    TextView userDetail;
    FirebaseAuth auth;
    FirebaseUser user;
    FloatingActionButton docbot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        predictionCard = findViewById(R.id.predictions);
        fitnessCard = findViewById(R.id.fitness);
        gpCard = findViewById(R.id.gp);
        userCard = findViewById(R.id.user);
        docbot = findViewById(R.id.fab_docbot);

        predictionCard.setOnClickListener(this);
        fitnessCard.setOnClickListener(this);
        gpCard.setOnClickListener(this);
        userCard.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        fab_logout = findViewById(R.id.fab_logout);
        userDetail = findViewById(R.id.userDetail);

        user = auth.getCurrentUser();
        if (user == null){
            Intent intent = new Intent(getApplicationContext(), login.class);
            startActivity(intent);
            finish();
        }else {
            userDetail.setText(user.getEmail());
            checkUserNode();
        }

        fab_logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), login.class);
            startActivity(intent);
            finish();
        });

        docbot.setOnClickListener( v -> {
            Intent intent = new Intent(getApplicationContext(), docBotActivity.class);
            startActivity(intent);

        });
    }

    private void checkUserNode() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Toast.makeText(dashboardActivity.this, "Please fill out your personal information", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), UserDetailsActivity2.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, navActivity1.class);

        if (view.getId() == R.id.predictions) {
            intent.putExtra("fragment_id", "predictions");
        } else if (view.getId() == R.id.fitness) {
            intent.putExtra("fragment_id", "fitness");
        } else if (view.getId() == R.id.gp) {
            intent.putExtra("fragment_id", "gp");
        } else if (view.getId() == R.id.user) {
            intent.putExtra("fragment_id", "user");
        }
        startActivity(intent);
    }
}