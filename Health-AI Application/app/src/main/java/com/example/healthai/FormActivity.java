package com.example.healthai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Button send1 = findViewById(R.id.button13);
        send1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send1 = new Intent(FormActivity.this, GPActivity.class);
                startActivity(send1);
            }
        });

        Button btnBack = findViewById(R.id.button12);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}