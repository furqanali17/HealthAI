package com.example.healthai.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthai.R;
import com.example.healthai.Insurance.insuranceDetailsActivity;
import com.example.healthai.UserDetailsActivity1;

public class UserFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        CardView cardView1 = view.findViewById(R.id.cardView1);
        CardView cardView2 = view.findViewById(R.id.cardView2);

        cardView1.setOnClickListener(v -> {
            Intent send_1 = new Intent(getActivity(), UserDetailsActivity1.class);
            startActivity(send_1);
        });

        cardView2.setOnClickListener(v -> {
            Intent send_2 = new Intent(getActivity(), insuranceDetailsActivity.class);
            startActivity(send_2);
        });

        return view;
    }
}