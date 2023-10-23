package com.example.healthai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button send1 = findViewById(R.id.button1);
        send1.setOnClickListener(view -> {
            Intent send11 = new Intent(MainActivity.this, SignUpActivity1.class);
            startActivity(send11);
        });

        Button send2 = findViewById(R.id.button2);
        send2.setOnClickListener(view -> {
            Intent send112 = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(send112);
        });
    }
}