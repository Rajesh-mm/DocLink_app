package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class doctor_register extends AppCompatActivity {

    EditText doctorName, signupEmail, signupPassword, mobileNum;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    RadioGroup radioGroupGender;
    RadioButton radioButtonMale, radioButtonFemale, radioButtonOthers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        doctorName = findViewById(R.id.doctor_name);
        signupEmail = findViewById(R.id.email);
        signupPassword = findViewById(R.id.password);
        mobileNum = findViewById(R.id.mobile_no);

        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        radioButtonOthers = findViewById(R.id.radioButtonOthers);

        signupButton = findViewById(R.id.btn_doctor);

        final Spinner spin_days = findViewById(R.id.day);
        final Spinner spin_months = findViewById(R.id.month);
        final Spinner spin_years = findViewById(R.id.year);

        List<String> days = new ArrayList<>();
        List<String> months = new ArrayList<>();
        List<String> years = new ArrayList<>();

        for (int i = 1; i <= 31; i++) {
            days.add(String.valueOf(i));
        }

        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        Collections.addAll(months, monthNames);

        // Populate the year spinner (1990 to 2024)
        for (int i = 1900; i <= 2024; i++) {
            years.add(String.valueOf(i));
        }

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);

        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin_days.setAdapter(dayAdapter);
        spin_months.setAdapter(monthAdapter);
        spin_years.setAdapter(yearAdapter);



        signupButton.setOnClickListener(v -> {

            database = FirebaseDatabase.getInstance();
            reference = database.getReference("doctor");

            String name = doctorName.getText().toString();
            String email = signupEmail.getText().toString();
            String password = signupPassword.getText().toString();
            String mobileNo = mobileNum.getText().toString();
            String day = spin_days.getSelectedItem().toString();
            String month = spin_months.getSelectedItem().toString();
            String year = spin_years.getSelectedItem().toString();

            HelperClass helperClass = new HelperClass(name, email, password, day, month, year, mobileNo);
            reference.child(name).setValue(helperClass);

            Toast.makeText(doctor_register.this, name ,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(doctor_register.this, Login.class);
            startActivity(intent);
        });

        loginRedirectText.setOnClickListener(v -> {
            Intent intent = new Intent(doctor_register.this,Login.class);
            startActivity(intent);
        });
    }
}