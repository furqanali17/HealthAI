package com.example.healthai.GP;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class gpFirebase {

    private DatabaseReference databaseReference;

    public gpFirebase() {
        // Initialize Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("gps"); // "gps" is the name of your database node
    }

    public void addGP(String name, String sex, String mobile) {
        // Generate a unique ID for the GP
        String gpId = databaseReference.push().getKey();

        // Create a GP object
        gpDetails gp = new gpDetails(name, sex, mobile);
        gp.setId(gpId);

        // Add the GP to the database
        assert gpId != null;
        databaseReference.child(gpId).setValue(gp);
    }
}
