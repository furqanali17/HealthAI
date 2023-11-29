package com.example.healthai.Insurance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthai.GP.gp_details_activity3;
import com.example.healthai.Profile.UserDetails;
import com.example.healthai.R;
import com.example.healthai.fragments.UserFragment;
import com.example.healthai.mainAppActivity.dashboardActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class insuranceDetailsActivity2 extends AppCompatActivity {

    Button callInsurance_Btn;
    String InsurancePhone;
    TextView insuranceCompanyTextView, insuranceYearTextView, policyNumberTextView, typeOfInsuranceTextView,
            subscriberIDTextView, groupNumberTextView, insurancePhoneTextView;
    CardView insurance_cardView;
    FloatingActionButton goBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_details2);

        insurance_cardView = findViewById(R.id.insurance_cardView);
        insuranceCompanyTextView = findViewById(R.id.insuranceCompanyTextView);
        insuranceYearTextView = findViewById(R.id.insuranceYearTextView);
        policyNumberTextView = findViewById(R.id.policyNumberTextView);
        typeOfInsuranceTextView = findViewById(R.id.typeOfInsuranceTextView);
        subscriberIDTextView = findViewById(R.id.subscriberIDTextView);
        groupNumberTextView = findViewById(R.id.groupNumberTextView);
        insurancePhoneTextView = findViewById(R.id.insurancePhoneTextView);

        getInsuranceInfo();
        callInsurance_Btn = findViewById(R.id.callInsurance_Btn);
        callInsurance_Btn.setOnClickListener(v -> action_dial());

        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(v -> {
            Intent intent = new Intent(insuranceDetailsActivity2.this, dashboardActivity.class);
            startActivity(intent);
        });
    }

    // Inside getInsuranceInfo() method
    private void getInsuranceInfo() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            String uid = user.getUid();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Insurance_Details");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    InsuranceDetails insuranceDetails = dataSnapshot.getValue(InsuranceDetails.class);
                    if (insuranceDetails != null) {
                        insuranceCompanyTextView.setText(insuranceDetails.getInsuranceCompany());
                        insuranceYearTextView.setText(insuranceDetails.getInsuranceYear());
                        policyNumberTextView.setText(insuranceDetails.getPolicyNumber());
                        typeOfInsuranceTextView.setText(insuranceDetails.getTypeOfInsurance());
                        subscriberIDTextView.setText(insuranceDetails.getSubscriberID());
                        groupNumberTextView.setText(insuranceDetails.getGroupNumber());
                        insurancePhoneTextView.setText(insuranceDetails.getInsurancePhone());

                        // Update InsurancePhone variable
                        InsurancePhone = insuranceDetails.getInsurancePhone();
                    } else {
                        Toast.makeText(insuranceDetailsActivity2.this, "Error fetching Insurance information", Toast.LENGTH_SHORT).show();
                    }
                }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle errors here
                        Toast.makeText(insuranceDetailsActivity2.this, "Error fetching Insurance information", Toast.LENGTH_SHORT).show();
                    }
                });
            //}
        }
    }


    public void action_dial(){
        if (InsurancePhone != null && !InsurancePhone.isEmpty()) {
            Intent intentDial = new Intent(Intent.ACTION_DIAL);
            Uri number = Uri.parse("tel:" + InsurancePhone);
            intentDial.setData(number);

            try {
                startActivity(intentDial);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(insuranceDetailsActivity2.this, "Error calling Insurance", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(insuranceDetailsActivity2.this, "Insurance mobile number not available", Toast.LENGTH_SHORT).show();
        }
    }
}