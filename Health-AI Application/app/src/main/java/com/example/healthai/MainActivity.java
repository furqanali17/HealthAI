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
        send1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send1 = new Intent(MainActivity.this, SignUpActivity1.class);
                startActivity(send1);
            }
        });

        Button send2 = findViewById(R.id.button2);
        send2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send1 = new Intent(MainActivity.this, LoginActivity.class);

                startActivity(send1);
            }
        });
    }
}