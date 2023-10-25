package com.example.healthai.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.healthai.R;
import com.example.healthai.formActivity.HealthFormActivity1;

public class predictionFragment extends Fragment{
    Button form_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prediction, container, false);
        form_button = view.findViewById(R.id.form_button);

        form_button.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HealthFormActivity1.class);
            startActivity(intent);
        });
        return view;
    }
}