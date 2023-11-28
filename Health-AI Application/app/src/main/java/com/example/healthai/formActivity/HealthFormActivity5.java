package com.example.healthai.formActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.healthai.R;

import java.util.concurrent.atomic.AtomicBoolean;

public class HealthFormActivity5 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_form5);

        // Get form
        Form userForm = getIntent().getParcelableExtra("form");

        // Page inputs

        AtomicBoolean stroke = new AtomicBoolean();
        AtomicBoolean difficultyWalking = new AtomicBoolean();
        AtomicBoolean asthma = new AtomicBoolean();
        AtomicBoolean kidneyDisease = new AtomicBoolean();
        AtomicBoolean prevSkinCancer= new AtomicBoolean();

        RadioGroup rgStroke = findViewById(R.id.rgFormStroke);
        RadioGroup rgDifficultyWalking = findViewById(R.id.rgFormDifficultyWalking);
        RadioGroup rgAsthma = findViewById(R.id.rgFormAsthma);
        RadioGroup rgKidneyDisease = findViewById(R.id.rgFormKidneyDisease);
        RadioGroup rgPrevSkinCancer = findViewById(R.id.rgFormPrevSkinCancer);



        Button send1 = findViewById(R.id.button13);
        send1.setOnClickListener(view -> {

            // check inputs
            boolean formComplete = true;

            formComplete = Form.validateRGInput(rgStroke,stroke,formComplete);
            formComplete = Form.validateRGInput(rgDifficultyWalking,difficultyWalking,formComplete);
            formComplete = Form.validateRGInput(rgAsthma,asthma,formComplete);
            formComplete = Form.validateRGInput(rgKidneyDisease,kidneyDisease,formComplete);
            formComplete = Form.validateRGInput(rgPrevSkinCancer,prevSkinCancer,formComplete);

            if(formComplete){
                Intent send11 = new Intent(HealthFormActivity5.this, HealthFormActivity6.class);
                userForm.page5Input(stroke.get(),difficultyWalking.get(),
                        asthma.get(),kidneyDisease.get(),prevSkinCancer.get());
                send11.putExtra("form",userForm);
                startActivity(send11);
            }            else
                Toast.makeText(this, "Form not complete.", Toast.LENGTH_SHORT).show();
        });
    }
}