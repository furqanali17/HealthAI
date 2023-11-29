package com.example.healthai.formActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.healthai.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.atomic.AtomicInteger;

public class HealthFormActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_form3);

        // Get form
        Form userForm = getIntent().getParcelableExtra("form");

        // page inputs
        AtomicInteger snore = new AtomicInteger();
        AtomicInteger frequentColds = new AtomicInteger();
        AtomicInteger dryCough = new AtomicInteger();
        AtomicInteger chestPain = new AtomicInteger();
        AtomicInteger swallowingDifficulty = new AtomicInteger();
        AtomicInteger shortnessOfBreath = new AtomicInteger();
        AtomicInteger weezing = new AtomicInteger();
        AtomicInteger weightLoss = new AtomicInteger();
        AtomicInteger fatigue = new AtomicInteger();
        AtomicInteger clubbingOfFingerNails = new AtomicInteger();
        AtomicInteger coughing = new AtomicInteger();

        // View
        TextInputEditText tiSnore = findViewById(R.id.tiFormSnore);
        TextInputEditText tiColds = findViewById(R.id.tiFormFrequentColds);
        TextInputEditText tiDryCough = findViewById(R.id.tiFormDryCough);
        TextInputEditText tiChestPain = findViewById(R.id.tiFormChestPain);
        TextInputEditText tiSwallowing = findViewById(R.id.tiFormSwallowingDifficulty);
        TextInputEditText tiShortBreath = findViewById(R.id.tiFormShortnessOfBreath);
        TextInputEditText tiWeezing = findViewById(R.id.tiFormWeezing);
        TextInputEditText tiWeightloss = findViewById(R.id.tiFormWeightLoss);
        TextInputEditText tiFatigue = findViewById(R.id.tiFormFatigue);
        TextInputEditText tiFingernailClubbing = findViewById(R.id.tiFormClubbingOfFingerNails);
        TextInputEditText tiFormCough = findViewById(R.id.tiFormCoughing);

        // set input
        tiSnore.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        tiColds.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        tiDryCough.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        tiChestPain.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        tiSwallowing.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        tiShortBreath.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        tiWeezing.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        tiWeightloss.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        tiFatigue.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        tiFingernailClubbing.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        tiFormCough.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);


        Button send1 = findViewById(R.id.button13);
        send1.setOnClickListener(view -> {
            // check inputs
            boolean formComplete = true;

            if (!tiSnore.getText().toString().matches("")) {
                int input = Integer.parseInt(tiSnore.getText().toString());
                if (Form.checkIntInput(1, 8, input))
                    snore.set(input);
                else
                    formComplete = false;
            } else
                formComplete = false;

            if (!tiColds.getText().toString().matches("")) {
                int input = Integer.parseInt(tiColds.getText().toString());
                if (Form.checkIntInput(1, 9, input))
                    frequentColds.set(input);
                else
                    formComplete = false;
            } else
                formComplete = false;

            if (!tiDryCough.getText().toString().matches("")) {
                int input = Integer.parseInt(tiDryCough.getText().toString());
                if (Form.checkIntInput(1, 9, input))
                    dryCough.set(input);
                else
                    formComplete = false;
            } else
                formComplete = false;

            // Example for tiChestPain
            if (!tiChestPain.getText().toString().matches("")) {
                int input = Integer.parseInt(tiChestPain.getText().toString());
                if (Form.checkIntInput(1, 9, input))
                    chestPain.set(input);
                else
                    formComplete = false;
            } else
                formComplete = false;

            if (!tiSwallowing.getText().toString().matches("")) {
                int input = Integer.parseInt(tiSwallowing.getText().toString());
                if (Form.checkIntInput(1, 8, input))
                    swallowingDifficulty.set(input);
                else
                    formComplete = false;
            } else
                formComplete = false;

            if (!tiShortBreath.getText().toString().matches("")) {
                int input = Integer.parseInt(tiShortBreath.getText().toString());
                if (Form.checkIntInput(1, 7, input))
                    shortnessOfBreath.set(input);
                else
                    formComplete = false;
            } else
                formComplete = false;

            if (!tiWeezing.getText().toString().matches("")) {
                int input = Integer.parseInt(tiWeezing.getText().toString());
                if (Form.checkIntInput(1, 8, input))
                    weezing.set(input);
                else
                    formComplete = false;
            } else
                formComplete = false;

            if (!tiWeightloss.getText().toString().matches("")) {
                int input = Integer.parseInt(tiWeightloss.getText().toString());
                if (Form.checkIntInput(1, 7, input))
                    weightLoss.set(input);
                else
                    formComplete = false;
            } else
                formComplete = false;

            if (!tiFatigue.getText().toString().matches("")) {
                int input = Integer.parseInt(tiFatigue.getText().toString());
                if (Form.checkIntInput(1, 9, input))
                    fatigue.set(input);
                else
                    formComplete = false;
            } else
                formComplete = false;

            if (!tiFingernailClubbing.getText().toString().matches("")) {
                int input = Integer.parseInt(tiFingernailClubbing.getText().toString());
                if (Form.checkIntInput(1, 7, input))
                    clubbingOfFingerNails.set(input);
                else
                    formComplete = false;
            } else
                formComplete = false;

            if (!tiFormCough.getText().toString().matches("")) {
                int input = Integer.parseInt(tiFormCough.getText().toString());
                if (Form.checkIntInput(1, 7, input))
                    coughing.set(input);
                else
                    formComplete = false;
            } else
                formComplete = false;

            if(formComplete){
                Intent send11 = new Intent(HealthFormActivity3.this, HealthFormActivity4.class);
                userForm.page3Input(snore.get(),frequentColds.get(),dryCough.get(),chestPain.get(),swallowingDifficulty.get(),
                        shortnessOfBreath.get(),weezing.get(),weightLoss.get(),fatigue.get(),clubbingOfFingerNails.get(),coughing.get());
                send11.putExtra("form",userForm);
                startActivity(send11);
            }
            else
                Toast.makeText(this, "Form not complete.", Toast.LENGTH_SHORT).show();
        });
    }
}