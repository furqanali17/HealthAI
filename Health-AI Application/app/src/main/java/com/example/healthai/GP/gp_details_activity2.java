package com.example.healthai.GP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.healthai.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class gp_details_activity2 extends AppCompatActivity implements RecyclerViewInterface{

    private List<gpDetails> gpList;
    RecyclerView rv;
    gpAdapter adapter;
    private DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gp_details2);

        rv = findViewById(R.id.gpRecyclerView);

        Intent intent = getIntent();
        String selectedSex = intent.getStringExtra("SELECTED_SEX");

        databaseReference = FirebaseDatabase.getInstance().getReference("Professionals");

        Query query = databaseReference.orderByChild("sex").equalTo(selectedSex);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gpList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    gpDetails gp = snapshot.getValue(gpDetails.class);
                    if (gp != null) {
                        gp.setId(snapshot.getKey());
                    }
                    gpList.add(gp);
                }

                rv.setLayoutManager(new LinearLayoutManager(gp_details_activity2.this));
                adapter = new gpAdapter(getApplicationContext(), gpList, gp_details_activity2.this);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error fetching GP list", error.toException());
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        gpDetails clickedGp = gpList.get(position);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        assert user != null;
        String uid = user.getUid();

        String gpId = clickedGp != null ? clickedGp.getId() : null;

        if (gpId != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);

            // Create a child node under "Professionals" with the ID of the selected GP
            databaseReference.child("Professionals").setValue(clickedGp)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(gp_details_activity2.this, "Successfully Updated", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(gp_details_activity2.this, "Failed to Update", Toast.LENGTH_LONG).show();
                        }
                    });

            // Add the GP ID to the user's attributes as "assignedProfessionalID"
            databaseReference.child("assignedProfessionalID").setValue(gpId)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(gp_details_activity2.this, "Successfully Assigned", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(gp_details_activity2.this, "Failed to Assign", Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Log.e("GP_DETAILS_ACTIVITY", "GP ID is null");
        }


    }
}