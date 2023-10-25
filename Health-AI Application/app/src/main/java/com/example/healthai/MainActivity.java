package com.example.healthai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.healthai.loginActivity.login;
import com.example.healthai.mainAppActivity.dashboardActivity;
import com.example.healthai.signupActivity.registerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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