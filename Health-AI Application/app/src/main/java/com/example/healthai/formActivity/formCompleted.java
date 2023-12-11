
package com.example.healthai.formActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.healthai.R;
import com.example.healthai.fragments.predictionFragment;
import com.example.healthai.mainAppActivity.dashboardActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class formCompleted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_completed);

        Form userForm = getIntent().getParcelableExtra("form");

        Button send1 = findViewById(R.id.button11);
        send1.setOnClickListener(view -> {

            saveFormDataToFirebase(userForm);

            Intent send11 = new Intent(formCompleted.this, predictionFragment.class);
            startActivity(send11);
        });
    }

    private void saveFormDataToFirebase(Form form) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        assert user != null;
        String userId = user.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("forms");

        // Remove any existing form
        databaseReference.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Add the new form
                databaseReference.push().setValue(form)
                        .addOnCompleteListener(task2 -> {
                            if (task2.isSuccessful()) {
                                Toast.makeText(this, "Form successfully added to the database", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Error: " + task2.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
