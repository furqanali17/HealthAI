package com.example.healthai.Insurance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthai.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class insuranceDetailsActivity extends AppCompatActivity {
    EditText editTextInsuranceCompany, editTextInsuranceYear, editTextPolicyNumber,
            editTextTypeOfInsurance, editTextSubscriberID, editTextGroupNumber,
            editTextInsurancePhone, editTextPolicyHolderName;

    String InsuranceCompany, InsuranceYear, PolicyNumber, TypeOfInsurance, SubscriberID, GroupNumber,
    InsurancePhone, PolicyHolderName;

    DatabaseReference databaseReference;
    FirebaseDatabase db;

    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_details);

        editTextInsuranceCompany = findViewById(R.id.editTextInsuranceCompany);
        editTextInsuranceYear = findViewById(R.id.editTextInsuranceYear);
        editTextPolicyNumber = findViewById(R.id.editTextPolicyNumber);
        editTextTypeOfInsurance = findViewById(R.id.editTextTypeOfInsurance);
        editTextSubscriberID = findViewById(R.id.editTextSubscriberID);
        editTextGroupNumber = findViewById(R.id.editTextGroupNumber);
        editTextInsurancePhone = findViewById(R.id.editTextInsurancePhone);
        editTextPolicyHolderName = findViewById(R.id.editTextPolicyHolderName);

        // Initializing Save Button
        buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(v -> {
            InsuranceCompany = editTextInsuranceCompany.getText().toString();
            InsuranceYear = editTextInsuranceYear.getText().toString();
            PolicyNumber =editTextPolicyNumber.getText().toString();
            TypeOfInsurance = editTextTypeOfInsurance.getText().toString();
            SubscriberID = editTextSubscriberID.getText().toString();
            GroupNumber = editTextGroupNumber.getText().toString();
            InsurancePhone = editTextInsurancePhone.getText().toString();
            PolicyHolderName = editTextPolicyHolderName.getText().toString();

            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace('.', ',');

            InsuranceDetails insuranceDetails = new InsuranceDetails(InsuranceCompany,
                    InsuranceYear, PolicyNumber, TypeOfInsurance, SubscriberID,
                    GroupNumber, InsurancePhone, PolicyHolderName);

            db = FirebaseDatabase.getInstance();
            databaseReference = db.getReference("Users").child(email);

            databaseReference.child("Insurance_Details").setValue(insuranceDetails).addOnCompleteListener(task -> {
                editTextInsuranceCompany.setText("");
                editTextInsuranceYear.setText("");
                editTextPolicyNumber.setText("");
                editTextTypeOfInsurance.setText("");
                editTextSubscriberID.setText("");
                editTextGroupNumber.setText("");
                editTextInsurancePhone.setText("");
                editTextPolicyHolderName.setText("");

                Toast.makeText(insuranceDetailsActivity.this, "Successfully Updated", Toast.LENGTH_LONG).show();
            });
        });
    }
}