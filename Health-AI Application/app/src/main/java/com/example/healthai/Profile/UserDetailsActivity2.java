package com.example.healthai.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthai.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDetailsActivity2 extends AppCompatActivity {
    EditText editTextName, editTextAge, editTextSex, editTextMobile;

    String name, sex, mobile, ageText;
    int age;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    FirebaseDatabase db;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details2);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        editTextName = findViewById(R.id.name_editText);
        editTextAge = findViewById(R.id.age_editText);
        editTextSex = findViewById(R.id.sex_editText);
        editTextMobile = findViewById(R.id.mobile_editText);

        btnSave = findViewById(R.id.buttonSave);
        btnSave.setOnClickListener(v -> {
            name = editTextName.getText().toString();
            ageText = editTextAge.getText().toString();
            age = Integer.parseInt(ageText);
            sex = editTextSex.getText().toString();
            mobile = editTextMobile.getText().toString();
            String email = user.getEmail();
            assert email != null;
            email = email.replace('.', ',');

            UserDetails userDetails = new UserDetails(name, sex, age, mobile);

            db = FirebaseDatabase.getInstance();
            databaseReference = db.getReference("Users");

            databaseReference.child(email).setValue(userDetails).addOnCompleteListener(task -> {
                editTextName.setText("");
                editTextAge.setText("");
                editTextSex.setText("");
                editTextMobile.setText("");

                Toast.makeText(UserDetailsActivity2.this, "Successfully Updated", Toast.LENGTH_LONG).show();
            });
        });
    }
}