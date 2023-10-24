package com.example.healthai.formActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.healthai.R;

public class HealthFormActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_form1);

        Button send1 = findViewById(R.id.button13);
        send1.setOnClickListener(view -> {
            Intent send11 = new Intent(HealthFormActivity1.this, HealthFormActivity2.class);
            startActivity(send11);
        });
    }
}