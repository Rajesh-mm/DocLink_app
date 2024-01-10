package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Register extends AppCompatActivity {

    Button button_reg_patient, button_reg_doctor, button_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        button_reg_patient=(Button) findViewById(R.id.btn_reg_patient);
        button_reg_doctor=(Button) findViewById(R.id.btn_reg_doctor);
        button_login=(Button) findViewById(R.id.btn_login);

        button_reg_patient.setOnClickListener(v -> {
            Intent btnregpatient = new Intent(Register.this, patient_register.class);
            btnregpatient.putExtra("Home", "Email");
            startActivity(btnregpatient);
            finish();
        });

        button_reg_doctor.setOnClickListener(v -> {
            Intent btnregdoctor = new Intent(Register.this, doctor_register.class);
            btnregdoctor.putExtra("Home", "Email");
            startActivity(btnregdoctor);
            finish();
        });

        button_login.setOnClickListener(v -> {
            Intent btnlogin = new Intent(Register.this,Login.class);
            startActivity(btnlogin);
            finish();
        });
    }
}