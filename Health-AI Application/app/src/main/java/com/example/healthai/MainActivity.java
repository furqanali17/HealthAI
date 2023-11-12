package com.example.healthai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.healthai.GP.gpFirebase;
import com.example.healthai.loginActivity.login;
import com.example.healthai.mainAppActivity.dashboardActivity;
import com.example.healthai.signupActivity.registerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    //private com.example.healthai.GP.gpFirebase gpFirebase;
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), dashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        gpFirebase = new gpFirebase();

        gpFirebase.addGP("Dr. John", "Male", "0897423467");
        gpFirebase.addGP("Dr. Charles", "Male", "0873642941");
        gpFirebase.addGP("Dr. Beatriz", "Female", "0836437234");*/


        Button send1 = findViewById(R.id.button1);
        send1.setOnClickListener(view -> {
            Intent send11 = new Intent(MainActivity.this, registerActivity.class);
            startActivity(send11);
        });

        Button send2 = findViewById(R.id.button2);
        send2.setOnClickListener(view -> {
            Intent send112 = new Intent(MainActivity.this, login.class);
            startActivity(send112);
        });
    }
}