
package com.example.healthai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class formCompleted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_completed);

        Button send1 = findViewById(R.id.button11);
        send1.setOnClickListener(view -> {
            Intent send11 = new Intent(formCompleted.this, SignedUpActivity.class);
            startActivity(send11);
        });
    }
}