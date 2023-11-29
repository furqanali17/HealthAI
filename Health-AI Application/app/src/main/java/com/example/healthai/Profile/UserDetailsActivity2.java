package com.example.healthai.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.healthai.R;
import com.example.healthai.fragments.UserFragment;
import com.example.healthai.mainAppActivity.dashboardActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UserDetailsActivity2 extends AppCompatActivity {
    EditText editTextName, editTextAge, editTextMobile;
    RadioGroup radioGroupSex;
    RadioButton radioButtonSex;

    String name, sex, mobile, ageText;
    int age;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    FirebaseDatabase db;
    Button btnSave;
    FloatingActionButton goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details2);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        editTextName = findViewById(R.id.name_editText);
        editTextAge = findViewById(R.id.age_editText);
        editTextMobile = findViewById(R.id.mobile_editText);
        radioGroupSex = findViewById(R.id.sex_radioGroup);

        btnSave = findViewById(R.id.buttonSave);
        btnSave.setOnClickListener(v -> {
            name = editTextName.getText().toString();
            ageText = editTextAge.getText().toString();
            age = Integer.parseInt(ageText);

            int selectedId = radioGroupSex.getCheckedRadioButtonId();
            radioButtonSex = findViewById(selectedId);
            sex = radioButtonSex.getText().toString();

            mobile = editTextMobile.getText().toString();
            String email = user.getEmail();
            String uid = user.getUid();

            db = FirebaseDatabase.getInstance();
            databaseReference = db.getReference("Users");

            Map<String, Object> userUpdates = new HashMap<>();
            userUpdates.put("name", name);
            userUpdates.put("sex", sex);
            userUpdates.put("age", age);
            userUpdates.put("mobile", mobile);
            userUpdates.put("email", email);

            databaseReference.child(uid).updateChildren(userUpdates).addOnCompleteListener(task -> {
                editTextName.setText("");
                editTextAge.setText("");
                radioGroupSex.clearCheck();
                editTextMobile.setText("");

                Toast.makeText(UserDetailsActivity2.this, "Successfully Updated", Toast.LENGTH_LONG).show();
            });
        });

        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(v -> {
            Intent intent = new Intent(UserDetailsActivity2.this, dashboardActivity.class);
            startActivity(intent);
        });

    }
}