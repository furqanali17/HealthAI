package com.example.healthai.mainAppActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import com.example.healthai.R;
import com.example.healthai.fragments.FitnessFragment;
import com.example.healthai.fragments.GpFragment;
import com.example.healthai.fragments.UserFragment;
import com.example.healthai.fragments.predictionFragment;

public class dashboardActivity extends AppCompatActivity implements View.OnClickListener{
    public CardView predictionCard, fitnessCard, gpCard, userCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        predictionCard = findViewById(R.id.predictions);
        fitnessCard = findViewById(R.id.fitness);
        gpCard = findViewById(R.id.gp);
        userCard = findViewById(R.id.user);

        predictionCard.setOnClickListener(this);
        fitnessCard.setOnClickListener(this);
        gpCard.setOnClickListener(this);
        userCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        if (view.getId() == R.id.predictions) {
            intent = new Intent(this, navActivity1.class);
            startActivity(intent);
        } else if (view.getId() == R.id.fitness) {
            intent = new Intent(this, navActivity1.class);
            startActivity(intent);
        }else if (view.getId() == R.id.gp) {
            intent = new Intent(this, navActivity1.class);
            startActivity(intent);
        }else if (view.getId() == R.id.user) {
            intent = new Intent(this, navActivity1.class);
            startActivity(intent);
        }
    }
}