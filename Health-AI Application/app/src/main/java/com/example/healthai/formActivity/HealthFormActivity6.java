package com.example.healthai.formActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.healthai.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class HealthFormActivity6 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_form6);

        // Get form
        Form userForm = getIntent().getParcelableExtra("form");

        // Page inputs
        AtomicReference<String> diabetic = new AtomicReference<>();
        AtomicReference<String> generalHealth = new AtomicReference<>();
        AtomicReference<String> race = new AtomicReference<String>();
        AtomicInteger avgSleepTime = new AtomicInteger();
        AtomicInteger bmi = new AtomicInteger();
        AtomicInteger physicalHealthRating = new AtomicInteger();
        AtomicInteger mentalHealthRating = new AtomicInteger();


        RadioGroup rgDiabetic = findViewById(R.id.rgFormDiabetic);
        RadioGroup rgGeneralHealth = findViewById(R.id.rgFormGeneralHealth);
        Spinner spRace = findViewById(R.id.spRace);
        TextInputEditText tiAvgSleep = findViewById(R.id.tiFormAverageSleepTime);
        TextInputEditText tiBmi = findViewById(R.id.tiFormBMI);
        TextInputEditText tiPhysHealthRating = findViewById(R.id.tiFormPhysicalHealthRating);
        TextInputEditText tiMentalHealth = findViewById(R.id.tiFormMentalHealthRating);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,R.array.race_array, android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRace.setAdapter(adapter);

        Button send1 = findViewById(R.id.button13);
        send1.setOnClickListener(view -> {
            // Check inputs
            boolean formComplete = true;

            formComplete = Form.validateRGInput(rgDiabetic,diabetic,formComplete);
            formComplete = Form.validateRGInput(rgGeneralHealth,generalHealth,formComplete);

            if(spRace.getSelectedItem()!= null){
                race.set(spRace.getSelectedItem().toString());
            }
            else
                formComplete = false;
            if (!tiAvgSleep.getText().toString().matches("") && formComplete)
                avgSleepTime.set(Integer.parseInt(tiAvgSleep.getText().toString()));
            else
                formComplete = false;

            if (!tiBmi.getText().toString().matches("") && formComplete)
                bmi.set(Integer.parseInt(tiBmi.getText().toString()));
            else
                formComplete = false;

            if (!tiPhysHealthRating.getText().toString().matches("") && formComplete)
                physicalHealthRating.set(Integer.parseInt(tiPhysHealthRating.getText().toString()));
            else
                formComplete = false;

            if (!tiMentalHealth.getText().toString().matches("") && formComplete)
                mentalHealthRating.set(Integer.parseInt(tiMentalHealth.getText().toString()));
            else
                formComplete = false;

            if (formComplete){
                Intent send11 = new Intent(HealthFormActivity6.this, formCompleted.class);
                userForm.page6Input(diabetic.get(),generalHealth.get(),race.get(),
                        avgSleepTime.get(),bmi.get(),physicalHealthRating.get(),mentalHealthRating.get());
                send11.putExtra("form",userForm);
                startActivity(send11);
            }            else
                Toast.makeText(this, "Form not complete.", Toast.LENGTH_SHORT).show();

        });
    }
}