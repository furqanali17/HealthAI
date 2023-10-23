package com.example.healthai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        Button send1 = findViewById(R.id.button7);
        send1.setOnClickListener(view -> {
            Intent send11 = new Intent(SignUpActivity2.this, HealthFormActivity1.class);
            startActivity(send11);
        });

        Button btnBack = findViewById(R.id.button6);
        btnBack.setOnClickListener(view -> onBackPressed());
    }
}