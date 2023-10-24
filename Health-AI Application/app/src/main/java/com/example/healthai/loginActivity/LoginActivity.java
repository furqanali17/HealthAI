package com.example.healthai.loginActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.healthai.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button send1 = findViewById(R.id.button);
        send1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send1 = new Intent(LoginActivity.this, LoggedInActivity.class);
                startActivity(send1);
            }
        });

        Button btnBack = findViewById(R.id.button5);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}