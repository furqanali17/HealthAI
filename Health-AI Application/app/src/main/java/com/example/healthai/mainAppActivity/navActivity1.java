package com.example.healthai.mainAppActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.healthai.R;
import com.example.healthai.fragments.FitnessFragment;
import com.example.healthai.fragments.GpFragment;
import com.example.healthai.fragments.UserFragment;
import com.example.healthai.fragments.predictionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class navActivity1 extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    com.example.healthai.fragments.predictionFragment predictionFragment = new predictionFragment();
    FitnessFragment fitnessFragment = new FitnessFragment();
    GpFragment gpFragment = new GpFragment();
    UserFragment userFragment = new UserFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav1);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, predictionFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.predictions) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, predictionFragment).commit();
            } else if (item.getItemId() == R.id.fitness) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fitnessFragment).commit();
            }else if (item.getItemId() == R.id.gp) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, gpFragment).commit();
            }else if (item.getItemId() == R.id.user) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, userFragment).commit();
            }

            return true;
        });
    }
}