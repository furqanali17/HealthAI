package com.example.healthai.Insurance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthai.GP.gp_details_activity3;
import com.example.healthai.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class insuranceDetailsActivity2 extends AppCompatActivity {

    Button callInsurance_Btn;
    String InsuranceCompany, InsuranceYear, PolicyNumber, TypeOfInsurance, SubscriberID, GroupNumber, InsurancePhone;
    TextView insurance_info_textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_details2);

        insurance_info_textView = findViewById(R.id.insurance_info_textView);

        getInsuranceInfo();
        callInsurance_Btn = findViewById(R.id.callInsurance_Btn);
        callInsurance_Btn.setOnClickListener(v -> action_dial());
    }

    private void getInsuranceInfo(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            String email = user.getEmail();
            if (email != null) {
                String userKey = email.replace('.', ',');

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userKey);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("Insurance_Details").exists()) {
                            InsuranceCompany = dataSnapshot.child("Insurance_Details").child("insuranceCompany").getValue(String.class);
                            InsuranceYear = dataSnapshot.child("Insurance_Details").child("insuranceYear").getValue(String.class);
                            PolicyNumber = dataSnapshot.child("Insurance_Details").child("policyNumber").getValue(String.class);
                            TypeOfInsurance = dataSnapshot.child("Insurance_Details").child("typeOfInsurance").getValue(String.class);
                            SubscriberID = dataSnapshot.child("Insurance_Details").child("subscriberID").getValue(String.class);
                            GroupNumber = dataSnapshot.child("Insurance_Details").child("groupNumber").getValue(String.class);
                            InsurancePhone = dataSnapshot.child("Insurance_Details").child("insurancePhone").getValue(String.class);

                            if (InsuranceCompany != null && InsuranceYear != null && PolicyNumber != null && TypeOfInsurance != null &&
                                    SubscriberID != null && GroupNumber != null && InsurancePhone != null) {
                                String InsuranceInfo = "Insurance Company:  " + InsuranceCompany + "\n" +
                                        "Insurance Year:  " + InsuranceYear + "\n" +
                                        "Policy Number:  " + PolicyNumber + "\n" +
                                        "Type Of Insurance:  " + TypeOfInsurance + "\n" +
                                        "Subscriber ID:  " + SubscriberID + "\n" +
                                        "Group Number:  " + GroupNumber + "\n" +
                                        "Insurance Phone:  " + InsurancePhone;
                                insurance_info_textView.setText(InsuranceInfo);
                            } else {
                                Toast.makeText(insuranceDetailsActivity2.this, "Error fetching Insurance information", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle errors here
                        Toast.makeText(insuranceDetailsActivity2.this, "Error fetching GP mobile number", Toast.LENGTH_SHORT).show();
                    }
                });
            }
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