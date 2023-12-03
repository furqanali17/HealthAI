package com.example.healthai.formActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.healthai.R;

import java.util.concurrent.atomic.AtomicBoolean;

public class HealthFormActivity4 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_form4);

        // Get form
        Form userForm = getIntent().getParcelableExtra("form");

        // page inputs
        AtomicBoolean highFatDiet = new AtomicBoolean();
        AtomicBoolean ibd = new AtomicBoolean();
        AtomicBoolean geneticRisk = new AtomicBoolean();
        AtomicBoolean prevColonCancer = new AtomicBoolean();
        AtomicBoolean prevCancer = new AtomicBoolean();
        AtomicBoolean prevRadiation = new AtomicBoolean();

        RadioGroup rgHighFatDiet = findViewById(R.id.rgFormHighFatDiet);

        RadioGroup rgIbd = findViewById(R.id.rgFormBowelDisease);
        RadioGroup rgFamilyHisCancer = findViewById(R.id.rgFormFamilyCancerHistory);
        RadioGroup rgPrevColonCancer = findViewById(R.id.rgFormPrevColonCancer);
        RadioGroup rgPrevCancer = findViewById(R.id.rgFormPreviousCancer);
        RadioGroup rgPrevRadiationTherapy = findViewById(R.id.rgPrevRadiationTherapy);
        Button send1 = findViewById(R.id.btnFormNext4);

        send1.setOnClickListener(view -> {

            // Check inputs
            boolean formComplete = true;

            formComplete = Form.validateRGInput(rgHighFatDiet,highFatDiet,formComplete);
            formComplete = Form.validateRGInput(rgIbd,ibd,formComplete);
            formComplete = Form.validateRGInput(rgFamilyHisCancer,geneticRisk,formComplete);
            formComplete = Form.validateRGInput(rgPrevColonCancer,prevColonCancer,formComplete);
            formComplete = Form.validateRGInput(rgPrevCancer,prevCancer,formComplete);
            formComplete = Form.validateRGInput(rgPrevRadiationTherapy,prevRadiation,formComplete);

            if(formComplete){
                Intent send11 = new Intent(HealthFormActivity4.this, HealthFormActivity5.class);
                userForm.page4Input(highFatDiet.get(),ibd.get(),geneticRisk.get(),prevColonCancer.get(),prevCancer.get());
                send11.putExtra("form",userForm);
                startActivity(send11);
            }else
                Toast.makeText(this, "Form not complete.", Toast.LENGTH_SHORT).show();
        });
    }
}