package com.example.healthai.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.healthai.GP.gp_details_activity3;
import com.example.healthai.R;
import com.example.healthai.GP.gp_details_activity1;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GpFragment extends Fragment {
    Button findGP_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gp, container, false);
        findGP_btn = view.findViewById(R.id.findGP_btn);
        findGP_btn.setEnabled(false);

        isGpAssigned();

        return view;
    }

    private void isGpAssigned(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            String uid = user.getUid();

            // Check if the GP is assigned in the database
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.hasChild("Professionals")) {
                        // If GP is not assigned, enable the button
                        findGP_btn.setEnabled(true);
                        findGP_btn.setOnClickListener(v -> {
                            Intent send = new Intent(getActivity(), gp_details_activity1.class);
                            startActivity(send);
                        });
                    } else {
                        // If GP is assigned, show a message
                        Toast.makeText(getActivity(), "GP is already assigned", Toast.LENGTH_SHORT).show();
                        Intent send = new Intent(getActivity(), gp_details_activity3.class);
                        startActivity(send);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting data failed, log a message
                    Log.w("Firebase", "loadPost:onCancelled", databaseError.toException());
                }
            });
        }
    }

}
