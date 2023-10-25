package com.example.healthai.mainAppActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.healthai.R;
import com.example.healthai.fragments.FitnessFragment;
import com.example.healthai.fragments.GpFragment;
import com.example.healthai.fragments.UserFragment;
import com.example.healthai.fragments.predictionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

public class navActivity1 extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    predictionFragment predictionFragment = new predictionFragment();
    FitnessFragment fitnessFragment = new FitnessFragment();
    GpFragment gpFragment = new GpFragment();
    UserFragment userFragment = new UserFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav1);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        String fragmentId = getIntent().getStringExtra("fragment_id");

        Fragment initialFragment = new Fragment();
        switch (Objects.requireNonNull(fragmentId)) {
            case "predictions":
                initialFragment = predictionFragment;
                bottomNavigationView.setSelectedItemId(R.id.predictions);
                break;
            case "fitness":
                initialFragment = fitnessFragment;
                bottomNavigationView.setSelectedItemId(R.id.fitness);
                break;
            case "gp":
                initialFragment = gpFragment;
                bottomNavigationView.setSelectedItemId(R.id.gp);
                break;
            case "user":
                initialFragment = userFragment;
                bottomNavigationView.setSelectedItemId(R.id.user);
                break;
            }

        if (initialFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, initialFragment).commit();
        }


        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.predictions) {
                selectedFragment = predictionFragment;
            } else if (item.getItemId() == R.id.fitness) {
                selectedFragment = fitnessFragment;
            } else if (item.getItemId() == R.id.gp) {
                selectedFragment = gpFragment;
            } else if (item.getItemId() == R.id.user) {
                selectedFragment = userFragment;
            }

            // Only replace the fragment if the selected item is different from the currently displayed item
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commit();
            }

            return true;
        });
    }
}