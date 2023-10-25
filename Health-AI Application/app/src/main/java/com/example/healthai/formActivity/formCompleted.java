
package com.example.healthai.formActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.healthai.R;
import com.example.healthai.mainAppActivity.dashboardActivity;

public class formCompleted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_completed);

        Button send1 = findViewById(R.id.button11);
        send1.setOnClickListener(view -> {
            Intent send11 = new Intent(formCompleted.this, dashboardActivity.class);
            startActivity(send11);
        });
    }
}