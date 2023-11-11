package com.example.healthai.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.healthai.R;

public class FitnessFragment extends Fragment {
    private EditText ageEditText, weightEditText, heightEditText, sexEditText;
    private TextView error_textView, bmiTextView, bmrTextView, bodyWaterTextView, bodyFatTextView, metAgeTextView, musMassTextView;
    private Button calcBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fitness, container, false);

        ageEditText = view.findViewById(R.id.age_editText);
        weightEditText = view.findViewById(R.id.weight_editText);
        heightEditText = view.findViewById(R.id.height_editText);
        sexEditText = view.findViewById(R.id.sex_editText);

        bmiTextView = view.findViewById(R.id.bmi_textView);
        bmrTextView = view.findViewById(R.id.bmr_textView);
        bodyWaterTextView = view.findViewById(R.id.bodyWater_textView);
        bodyFatTextView = view.findViewById(R.id.bodyFat_textView);
        metAgeTextView = view.findViewById(R.id.metAge_textView);
        musMassTextView = view.findViewById(R.id.musMass_textView);

        calcBtn = view.findViewById(R.id.calcBtn);
        error_textView = view.findViewById(R.id.error_textView);

        calcBtn.setOnClickListener(v -> {
            calculateBMI();
            calculateBMR();
            calculateBodyWater();
            calculateBodyFat();
            calculateMetabolicAge();
            calculateMuscleMass();
        });
        return view;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void calculateBMI(){
        String ageStr = ageEditText.getText().toString();
        String weightStr = weightEditText.getText().toString();
        String heightStr = heightEditText.getText().toString();

        if (ageStr.isEmpty() || weightStr.isEmpty() || heightStr.isEmpty()) {
            error_textView.setText("Please enter all details");
            return;
        }
        float weight = Float.parseFloat(weightStr);
        float height = Float.parseFloat(heightStr) / 100;

        // Calculate BMI
        float bmi = weight / (height * height);

        // Display the calculated BMI in the TextView
        bmiTextView.setText(String.format("%.2f", bmi));
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void calculateBMR() {
        String ageStr = ageEditText.getText().toString();
        String weightStr = weightEditText.getText().toString();
        String heightStr = heightEditText.getText().toString();
        String sexStr = sexEditText.getText().toString().toUpperCase(); // Convert to uppercase for case-insensitivity

        if (ageStr.isEmpty() || weightStr.isEmpty() || heightStr.isEmpty() || sexStr.isEmpty()) {
            error_textView.setText("Please enter all details");
            return;
        }

        // Check if sex is either "M" or "F"
        if (!sexStr.equals("M") && !sexStr.equals("F")) {
            error_textView.setText("Please enter 'M' or 'F' for sex");
            return;
        }

        // Convert values to numbers
        float age = Float.parseFloat(ageStr);
        float weight = Float.parseFloat(weightStr);
        float height = Float.parseFloat(heightStr) / 100; // Convert height to meters

        // Calculate BMR using Harris-Benedict equation
        float bmr;
        if (sexStr.equals("M")) {
            bmr = 88.362f + (13.397f * weight) + (4.799f * height * 100) - (5.677f * age);
        } else {
            bmr = 447.593f + (9.247f * weight) + (3.098f * height * 100) - (4.330f * age);
        }

        // Display the calculated BMR in the TextView
        bmrTextView.setText(String.format("%.2f", bmr));
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void calculateBodyWater() {
        String weightStr = weightEditText.getText().toString();

        if (weightStr.isEmpty()) {
            error_textView.setText("Please enter weight");
            return;
        }

        // Convert weight to a number
        float weight = Float.parseFloat(weightStr);

        // Assume body water is 60% of total body weight (adjust this percentage as needed)
        float bodyWaterPercentage = 60f;
        float bodyWater = (bodyWaterPercentage / 100) * weight;

        // Display the calculated body water in the TextView
        bodyWaterTextView.setText(String.format("%.2f kg", bodyWater));
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void calculateBodyFat() {
        String ageStr = ageEditText.getText().toString();
        String weightStr = weightEditText.getText().toString();
        String heightStr = heightEditText.getText().toString();
        String sexStr = sexEditText.getText().toString().toUpperCase(); // Convert to uppercase for case-insensitivity

        if (ageStr.isEmpty() || weightStr.isEmpty() || heightStr.isEmpty() || sexStr.isEmpty()) {
            error_textView.setText("Please enter all details");
            return;
        }

        // Check if sex is either "M" or "F"
        if (!sexStr.equals("M") && !sexStr.equals("F")) {
            error_textView.setText("Please enter 'M' or 'F' for sex");
            return;
        }

        // Convert values to numbers
        float age = Float.parseFloat(ageStr);
        float weight = Float.parseFloat(weightStr);
        float height = Float.parseFloat(heightStr) / 100; // Convert height to meters

        // Calculate BMI
        float bmi = weight / (height * height);

        // Estimate body fat using the YMCA formula
        float bodyFat;
        if (sexStr.equals("M")) {
            bodyFat = (1.20f * bmi) + (0.23f * age) - 16.2f;
        } else {
            bodyFat = (1.20f * bmi) + (0.23f * age) - 5.4f;
        }

        // Display the estimated body fat in the TextView
        bodyFatTextView.setText(String.format("%.2f%%", bodyFat));
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void calculateMetabolicAge() {
        String ageStr = ageEditText.getText().toString();
        String weightStr = weightEditText.getText().toString();
        String heightStr = heightEditText.getText().toString();
        String sexStr = sexEditText.getText().toString().toUpperCase(); // Convert to uppercase for case-insensitivity

        if (ageStr.isEmpty() || weightStr.isEmpty() || heightStr.isEmpty() || sexStr.isEmpty()) {
            error_textView.setText("Please enter all details");
            return;
        }

        // Check if sex is either "M" or "F"
        if (!sexStr.equals("M") && !sexStr.equals("F")) {
            error_textView.setText("Please enter 'M' or 'F' for sex");
            return;
        }

        // Convert values to numbers
        float age = Float.parseFloat(ageStr);
        float weight = Float.parseFloat(weightStr);
        float height = Float.parseFloat(heightStr) / 100; // Convert height to meters

        // Calculate BMI
        float bmi = weight / (height * height);

        // Estimate metabolic age based on a simple formula
        float metabolicAge;
        if (sexStr.equals("M")) {
            metabolicAge = (0.18f * age) + (0.07f * weight) + (0.03f * height) + 8;
        } else {
            metabolicAge = (0.16f * age) + (0.06f * weight) + (0.03f * height) + 5;
        }

        // Display the estimated metabolic age in the TextView
        metAgeTextView.setText(String.format("%.2f", metabolicAge));
    }
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void calculateMuscleMass() {
        String ageStr = ageEditText.getText().toString();
        String weightStr = weightEditText.getText().toString();
        String heightStr = heightEditText.getText().toString();
        String sexStr = sexEditText.getText().toString().toUpperCase(); // Convert to uppercase for case-insensitivity

        if (ageStr.isEmpty() || weightStr.isEmpty() || heightStr.isEmpty() || sexStr.isEmpty()) {
            error_textView.setText("Please enter all details");
            return;
        }

        // Check if sex is either "M" or "F"
        if (!sexStr.equals("M") && !sexStr.equals("F")) {
            error_textView.setText("Please enter 'M' or 'F' for sex");
            return;
        }

        // Convert values to numbers
        float age = Float.parseFloat(ageStr);
        float weight = Float.parseFloat(weightStr);
        float height = Float.parseFloat(heightStr) / 100; // Convert height to meters

        // Calculate BMI
        float bmi = weight / (height * height);

        // Estimate muscle mass using a general formula
        float muscleMass;
        if (sexStr.equals("M")) {
            muscleMass = (bmi * 1.1f) + (0.03f * age) + 0.4f;
        } else {
            muscleMass = (bmi * 1.07f) + (0.03f * age) + 0.2f;
        }

        musMassTextView.setText(String.format("%.2f kg", muscleMass));
    }


}