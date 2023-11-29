package com.example.healthai.formActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.healthai.R;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class HealthFormActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_form1);

        // Create a new form
        Form userForm = new Form();
        // form inputs
        AtomicInteger age = new AtomicInteger();
        AtomicBoolean gender = new AtomicBoolean(false);
        AtomicBoolean smoker = new AtomicBoolean(false);
        AtomicBoolean alcohol = new AtomicBoolean(false);
        AtomicBoolean obesity = new AtomicBoolean(false);
        AtomicBoolean physicalactivity = new AtomicBoolean(false);

        EditText tiAge = findViewById(R.id.age_editText);
        tiAge.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        RadioGroup rgGender = findViewById(R.id.rgFormGender);
        RadioGroup rgSmoker = findViewById(R.id.rgFormSmoker);
        RadioGroup rgHasAlcohol = findViewById(R.id.rgFormAlcohol);
        RadioGroup rgObesity = findViewById(R.id.rgFormObesity);
        RadioGroup rgPhysicalActivity = findViewById(R.id.rgFormPhysicalActivity);

        Button send1 = findViewById(R.id.button13);


        // if form complete, send intent
        send1.setOnClickListener(view -> {
            boolean formComplete = true;
            // Age
            if (!tiAge.getText().toString().matches(""))
                age.set(Integer.parseInt(tiAge.getText().toString()));
            else
                formComplete = false;

            // Gender
            formComplete = Form.validateRGInput(rgGender,gender,formComplete,"Male");

            // smoker
            formComplete = Form.validateRGInput(rgSmoker,smoker, formComplete);

            // alcohol
            formComplete = Form.validateRGInput(rgHasAlcohol,alcohol,formComplete);


            // obesity
            formComplete = Form.validateRGInput(rgObesity,obesity,formComplete);

            // physical activity
            formComplete = Form.validateRGInput(rgPhysicalActivity,physicalactivity,formComplete);


            if (formComplete){
                userForm.page1Input(age.get(),gender.get(),
                        smoker.get(),alcohol.get(),obesity.get(),physicalactivity.get());
                Intent send11 = new Intent(HealthFormActivity1.this, HealthFormActivity2.class);
                send11.putExtra("form", userForm);
                startActivity(send11);
            }
            else{
                Toast.makeText(this, "Form not complete.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}