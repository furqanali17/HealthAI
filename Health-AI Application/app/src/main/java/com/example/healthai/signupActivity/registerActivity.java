package com.example.healthai.signupActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthai.R;
import com.example.healthai.loginActivity.login;
import com.example.healthai.mainAppActivity.dashboardActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class registerActivity extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    TextView loginNow;
    Button buttonReg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    FirebaseDatabase db;
    DatabaseReference reference;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), dashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        loginNow = findViewById(R.id.loginNow);
        progressBar = findViewById(R.id.progressBar);

        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send = new Intent(registerActivity.this, login.class);
                startActivity(send);
                finish();
            }
        });

        buttonReg = findViewById(R.id.registerButton);
        buttonReg.setOnClickListener(view -> {
            String email, password;
            progressBar.setVisibility(View.VISIBLE);

            email = String.valueOf(editTextEmail.getText());
            password = String.valueOf(editTextPassword.getText());

            if (TextUtils.isEmpty(email)){
                Toast.makeText(registerActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)){
                Toast.makeText(registerActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                return;
            }


            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(registerActivity.this, "Authentication Created.",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(registerActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}