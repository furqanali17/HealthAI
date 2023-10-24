package com.example.healthai.loginActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.healthai.MainActivity;
import com.example.healthai.R;

public class LoggedInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        Button send1 = findViewById(R.id.button9);
        send1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send1 = new Intent(LoggedInActivity.this, MainActivity.class);
                startActivity(send1);
            }
        });
    }
}