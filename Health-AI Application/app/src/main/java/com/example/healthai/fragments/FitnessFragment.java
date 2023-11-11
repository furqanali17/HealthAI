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
    private EditText ageEditText, weightEditText, heightEditText;
    private TextView error_textView, bmiTextView;
    private Button calcBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fitness, container, false);

        ageEditText = view.findViewById(R.id.age_editText);
        weightEditText = view.findViewById(R.id.weight_editText);
        heightEditText = view.findViewById(R.id.height_editText);
        bmiTextView = view.findViewById(R.id.bmi_textView);
        calcBtn = view.findViewById(R.id.calcBtn);
        error_textView = view.findViewById(R.id.error_textView);

        calcBtn.setOnClickListener(v -> {
            calculateBMI();
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
}