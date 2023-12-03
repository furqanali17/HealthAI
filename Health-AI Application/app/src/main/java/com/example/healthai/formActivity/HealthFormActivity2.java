package com.example.healthai.formActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.healthai.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.atomic.AtomicInteger;

public class HealthFormActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_form2);



        // Get form
        Form userForm = getIntent().getParcelableExtra("form");
        // Page Inputs
        AtomicInteger alcohol = new AtomicInteger();
        AtomicInteger polution = new AtomicInteger();
        AtomicInteger smoke = new AtomicInteger();
        AtomicInteger hazards = new AtomicInteger();
        AtomicInteger diet = new AtomicInteger();
        AtomicInteger obesity = new AtomicInteger();
        AtomicInteger passive_smoke = new AtomicInteger() ;
        AtomicInteger dust_allergies = new AtomicInteger();
//        AtomicInteger history_lung_cancer = new AtomicInteger();
        AtomicInteger chronic_lung_disease = new AtomicInteger();

        TextInputEditText tiAlcohol = findViewById(R.id.tiFormConsumeAlcohol);
        TextInputEditText tiPolution = findViewById(R.id.tiFormAirPolution);
        TextInputEditText tiSmoke = findViewById(R.id.tiFormFrequentlySmoke);
        TextInputEditText tiHazards = findViewById(R.id.tiFormOccupationalHazards);
        TextInputEditText tiDiet = findViewById(R.id.tiFormBalancedDiet);
        TextInputEditText tiObesity = findViewById(R.id.tiFormObesity);
        TextInputEditText tiPassiveSmoke = findViewById(R.id.tiFormPassiveSmoke);
        TextInputEditText tiDustALlergy= findViewById(R.id.tiFormDustAllergies);
//        TextInputEditText tiLungCancerHist= findViewById(R.id.tiFormGeneticRisk);
        TextInputEditText tiChronicLung= findViewById(R.id.tiFormChronicLungDisease);

        // Set keyboard inputs
        tiAlcohol.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        tiPolution.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        tiSmoke.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        tiHazards.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        tiDiet.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        tiObesity.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        tiPassiveSmoke.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        tiDustALlergy.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
//        tiLungCancerHist.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        tiChronicLung.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);



        Button send1 = findViewById(R.id.button13);
        send1.setOnClickListener(view -> {
            // Get inputs
            boolean formComplete = true;
            if(!tiAlcohol.getText().toString().matches("")){
                int input = Integer.parseInt(tiAlcohol.getText().toString());
                if (Form.checkIntInput(1,8,input))
                    alcohol.set(input);
                else
                    formComplete = false;
            }
            else
                formComplete = false;

            if(!tiPolution.getText().toString().matches("")){
                int input = Integer.parseInt(tiPolution.getText().toString());
                if(Form.checkIntInput(1,8,input))
                    polution.set(input);
                else
                    formComplete = false;
            }
            else
                formComplete = false;

            if(!tiSmoke.getText().toString().matches("")){
                int input = Integer.parseInt(tiSmoke.getText().toString());
                if(Form.checkIntInput(1,8,input))
                    smoke.set(input);
                else
                    formComplete = false;
            }
            else
                formComplete = false;

            if(!tiHazards.getText().toString().matches("")){
                int input = Integer.parseInt(tiHazards.getText().toString());
                if(Form.checkIntInput(1,8,input))
                    hazards.set(input);
                else
                    formComplete = false;
            }
            else
                formComplete = false;

            if(!tiDiet.getText().toString().matches("")){
                int input = Integer.parseInt(tiDiet.getText().toString());
                if(Form.checkIntInput(1,7,input))
                    diet.set(input);
                else formComplete = false;
            }
            else
                formComplete = false;


            if(!tiObesity.getText().toString().matches("")){
                int input = Integer.parseInt(tiObesity.getText().toString());
                if(Form.checkIntInput(1,7,input))
                    obesity.set(input);
                else
                    formComplete = false;
            }
            else
                formComplete = false;


            if(!tiPassiveSmoke.getText().toString().matches("")){
                int input = Integer.parseInt(tiPassiveSmoke.getText().toString());
                if(Form.checkIntInput(1,8,input))
                    passive_smoke.set(input);
                else
                    formComplete = false;
            }
            else
                formComplete = false;


            if(!tiDustALlergy.getText().toString().matches("")){
                int input = Integer.parseInt(tiDustALlergy.getText().toString());
                if(Form.checkIntInput(1,8,input))
                    dust_allergies.set(input);
                else
                    formComplete = false;
            }
            else
                formComplete = false;

//            if (!tiLungCancerHist.getText().toString().matches("")) {
//                int input = Integer.parseInt(tiLungCancerHist.getText().toString());
//                if (Form.checkIntInput(1, 7, input))
//                    history_lung_cancer.set(input);
//                else
//                    formComplete = false;
//            } else
//                formComplete = false;

            if (!tiChronicLung.getText().toString().matches("")) {
                int input = Integer.parseInt(tiChronicLung.getText().toString());
                if (Form.checkIntInput(1, 7, input))
                    chronic_lung_disease.set(input);
                else
                    formComplete = false;
            } else
                formComplete = false;


            if (formComplete){
                Intent send11 = new Intent(HealthFormActivity2.this, HealthFormActivity3.class);
                userForm.page2Input(alcohol.get(),polution.get(),smoke.get(),
                        hazards.get(),diet.get(),obesity.get(),passive_smoke.get(),dust_allergies.get(),chronic_lung_disease.get());
                send11.putExtra("form", userForm);
                startActivity(send11);
            }
            else{
                Toast.makeText(this, "Form not complete.", Toast.LENGTH_SHORT).show();
            }

        });
    }
}