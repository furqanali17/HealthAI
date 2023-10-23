package com.example.healthai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class HealthFormActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_form2);

        Button send1 = findViewById(R.id.button13);
        send1.setOnClickListener(view -> {
            Intent send11 = new Intent(HealthFormActivity2.this, HealthFormActivity3.class);
            startActivity(send11);
        });
    }
}