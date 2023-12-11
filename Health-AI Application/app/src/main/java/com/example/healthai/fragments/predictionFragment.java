package com.example.healthai.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.healthai.R;
import com.example.healthai.formActivity.HealthFormActivity1;
import com.example.healthai.formActivity.predictActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class predictionFragment extends Fragment{
    CardView form_button, predict_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prediction, container, false);

        form_button = view.findViewById(R.id.cardView1);
        form_button.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HealthFormActivity1.class);
            startActivity(intent);
        });

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        assert user != null;
        String userId = user.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("forms");

        predict_btn = view.findViewById(R.id.cardView2);
        predict_btn.setOnClickListener(v -> {
            databaseReference.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        Intent intent = new Intent(getActivity(), predictActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Fill out the HealthAI form first", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), HealthFormActivity1.class);
                        startActivity(intent);
                    }
                }
            });
        });
        return view;
    }
}