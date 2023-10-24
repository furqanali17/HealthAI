package com.example.healthai.signupActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.healthai.R;
import com.example.healthai.mainAppActivity.dashboardActivity;

public class SignedUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_up);

        Button send1 = findViewById(R.id.button12);
        send1.setOnClickListener(view -> {
            Intent send11 = new Intent(SignedUpActivity.this, dashboardActivity.class);
            startActivity(send11);
        });
    }
}