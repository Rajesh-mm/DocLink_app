package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity {

    EditText loginEmail, loginPassword;
    Button button_patient, button_doctor;

    TextView button_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = findViewById(R.id.email);
        loginPassword = findViewById(R.id.password);
        button_patient = findViewById(R.id.btn_patient);
        button_doctor = findViewById(R.id.btn_doctor);
        button_register = findViewById(R.id.btn_register);


        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btnregister = new Intent(Login.this, Register.class);
                startActivity(btnregister);
            }
        });
    }

    public Boolean validateEmail() {
        String val = loginEmail.getText().toString();
        if(val.isEmpty()){
            loginEmail.setError("Email ID cannot be empty");
            return false;
        } else {
            loginEmail.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String val = loginPassword.getText().toString();
        if(val.isEmpty()){
            loginPassword.setError("Password cannot be empty");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }

    public void checkUser(View view) {
        String userEmail = loginEmail.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        loginEmail.setError(null);

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            String uid = user.getUid();

                            // Assuming you have a "users" node in the Realtime Database
                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(uid);

                            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        String role = snapshot.child("role").getValue(String.class);

                                        // Redirect based on the role
                                        if ("patient".equals(role)) {
                                            // Launch PatientActivity
                                            Intent intent = new Intent(Login.this, patient_home_page.class);
                                            startActivity(intent);
                                        } else if ("doctor".equals(role)) {
                                            // Launch DoctorActivity
                                            Intent intent = new Intent(Login.this, Doctor_home_page.class);
                                            startActivity(intent);
                                        }
                                    } else {
                                        // User data not found in the Realtime Database
                                        loginEmail.setError("User data not found");
                                        loginEmail.requestFocus();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    // Handle the failure
                                    Log.e("FirebaseDatabase", "Failed to read user data", error.toException());
                                }
                            });
                        }
                    } else {
                        loginPassword.setError("Invalid Credentials");
                        loginPassword.requestFocus();
                    }
                });
    }


}