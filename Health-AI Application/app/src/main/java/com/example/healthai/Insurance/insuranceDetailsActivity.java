package com.example.healthai.Insurance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthai.GP.gp_details_activity1;
import com.example.healthai.GP.gp_details_activity3;
import com.example.healthai.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class insuranceDetailsActivity extends AppCompatActivity {
    EditText editTextInsuranceCompany, editTextInsuranceYear, editTextPolicyNumber,
            editTextTypeOfInsurance, editTextSubscriberID, editTextGroupNumber,
            editTextInsurancePhone;

    String InsuranceCompany, InsuranceYear, PolicyNumber, TypeOfInsurance, SubscriberID, GroupNumber,
    InsurancePhone;

    DatabaseReference databaseReference;
    FirebaseDatabase db;
    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_details);

        // Initializing Save Button
        buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setEnabled(false);

        isInsuranceAdded();

        editTextInsuranceCompany = findViewById(R.id.editTextInsuranceCompany);
        editTextInsuranceYear = findViewById(R.id.editTextInsuranceYear);
        editTextPolicyNumber = findViewById(R.id.editTextPolicyNumber);
        editTextTypeOfInsurance = findViewById(R.id.editTextTypeOfInsurance);
        editTextSubscriberID = findViewById(R.id.editTextSubscriberID);
        editTextGroupNumber = findViewById(R.id.editTextGroupNumber);
        editTextInsurancePhone = findViewById(R.id.editTextInsurancePhone);

        buttonSave.setOnClickListener(v -> {
            InsuranceCompany = editTextInsuranceCompany.getText().toString();
            InsuranceYear = editTextInsuranceYear.getText().toString();
            PolicyNumber =editTextPolicyNumber.getText().toString();
            TypeOfInsurance = editTextTypeOfInsurance.getText().toString();
            SubscriberID = editTextSubscriberID.getText().toString();
            GroupNumber = editTextGroupNumber.getText().toString();
            InsurancePhone = editTextInsurancePhone.getText().toString();

            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace('.', ',');

            InsuranceDetails insuranceDetails = new InsuranceDetails(InsuranceCompany,
                    InsuranceYear, PolicyNumber, TypeOfInsurance, SubscriberID,
                    GroupNumber, InsurancePhone);

            db = FirebaseDatabase.getInstance();
            databaseReference = db.getReference("Users").child(email);

            databaseReference.child("Insurance_Details").setValue(insuranceDetails).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    editTextInsuranceCompany.setText("");
                    editTextInsuranceYear.setText("");
                    editTextPolicyNumber.setText("");
                    editTextTypeOfInsurance.setText("");
                    editTextSubscriberID.setText("");
                    editTextGroupNumber.setText("");
                    editTextInsurancePhone.setText("");

                    Toast.makeText(insuranceDetailsActivity.this, "Successfully Updated", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(insuranceDetailsActivity.this, "Failed to update insurance details", Toast.LENGTH_LONG).show();
                    Log.e("Firebase", "Error updating insurance details", task.getException());
                }
                });
        });
    }

    private void isInsuranceAdded(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            String email = user.getEmail();
            if (email != null) {
                String userKey = email.replace('.', ',');

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userKey);
                databaseReference.child("Insurance_Details").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            buttonSave.setEnabled(true);
                        } else {
                            // If GP is assigned, show a message
                            Toast.makeText(insuranceDetailsActivity.this, "Insurance details are already added", Toast.LENGTH_SHORT).show();
                            Intent send = new Intent(insuranceDetailsActivity.this, insuranceDetailsActivity2.class);
                            startActivity(send);
                        }
                    }

                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("Firebase", "loadPost:onCancelled", databaseError.toException());
                    }
                });
            }
        }
    }
}