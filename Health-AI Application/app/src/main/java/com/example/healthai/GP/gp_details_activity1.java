package com.example.healthai.GP;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.healthai.R;

public class gp_details_activity1 extends AppCompatActivity {
    Button searchGP_btn;
    RadioGroup sexRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gp_details1);

        sexRadioGroup = findViewById(R.id.sex_rdg);
        searchGP_btn = findViewById(R.id.searchGP_btn);

        searchGP_btn.setOnClickListener(v -> {
            int selectedId = sexRadioGroup.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(selectedId);

            if (selectedRadioButton != null) {
                String selectedSex = selectedRadioButton.getText().toString();

                // Pass the selected sex as an extra to the next activity
                Intent send = new Intent(gp_details_activity1.this, gp_details_activity2.class);
                send.putExtra("SELECTED_SEX", selectedSex);
                startActivity(send);
            } else {
                Toast.makeText(gp_details_activity1.this, "Please select a gender", Toast.LENGTH_SHORT).show();
            }
        });
    }
}