package com.example.healthai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class HealthFormActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_form3);

        Button send1 = findViewById(R.id.button13);
        send1.setOnClickListener(view -> {
            Intent send11 = new Intent(HealthFormActivity3.this, formCompleted.class);
            startActivity(send11);
        });
    }
}