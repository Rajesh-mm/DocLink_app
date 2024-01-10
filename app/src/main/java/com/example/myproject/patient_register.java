package com.example.myproject;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class patient_register extends AppCompatActivity {

    TextInputLayout Patientname, Mobileno, Email, Pass;
    Spinner Day, Month, Year;
    RadioGroup radioGroupGender;
    FirebaseAuth FAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    Button signup;
    String patientname, mobileno, emailid, password, day, month, year, role="patient";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);

        firebaseDatabase = FirebaseDatabase.getInstance();

        Patientname = (TextInputLayout)findViewById(R.id.patient_name);
        Mobileno = (TextInputLayout)findViewById(R.id.mobile_no);
        Email = (TextInputLayout)findViewById(R.id.email);
        Pass = (TextInputLayout)findViewById(R.id.password);


        Day = (Spinner) findViewById(R.id.day);
        Month = (Spinner) findViewById(R.id.month);
        Year = (Spinner) findViewById(R.id.year);

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

        Day.setAdapter(dayAdapter);
        Month.setAdapter(monthAdapter);
        Year.setAdapter(yearAdapter);

        Day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Retrieve the selected day
                day = (String) parentView.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        Month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                month = (String) parentView.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        Year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                year = (String) parentView.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        Day.setPrompt("Day");
        Month.setPrompt("Month");
        Year.setPrompt("Year");


        signup = (Button)findViewById(R.id.btn_signup_patient);


        radioGroupGender = findViewById(R.id.radioGroupGender);
        databaseReference = FirebaseDatabase.getInstance().getReference("patient");
        FAuth = FirebaseAuth.getInstance();

        radioGroupGender.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);

            String selectedGender = radioButton.getText().toString();

            FirebaseUser currentUser = FAuth.getCurrentUser();

            if (currentUser != null) {
                String userID = currentUser.getUid();

                // Store the selected gender in the Realtime Database
                databaseReference.child(userID).child("gender").setValue(selectedGender);
            }
        });

        signup.setOnClickListener(v -> {

            if (Patientname.getEditText() != null && Mobileno.getEditText() != null &&
                    Email.getEditText() != null && Pass.getEditText() != null) {
                patientname = Patientname.getEditText().getText().toString().trim();
                mobileno = Mobileno.getEditText().getText().toString().trim();
                emailid = Email.getEditText().getText().toString().trim();
                password = Pass.getEditText().getText().toString().trim();

                if (isValid()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(patient_register.this);
                    builder.setCancelable(false);
                    builder.setView(R.layout.progress_dialog_layout); // Use a layout with a ProgressBar
                    AlertDialog mDialog = builder.create();
                    mDialog.show();


                    FAuth.createUserWithEmailAndPassword(emailid, password).addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            FirebaseUser currentUser = FAuth.getCurrentUser();

                            if (currentUser != null) {
                                String useridd = currentUser.getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference("User").child(useridd);

                                final HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("Role", role);

                                databaseReference.setValue(hashMap).addOnCompleteListener(task1 -> {

                                    HashMap<String, String> hashMap1 = new HashMap<>();
                                    hashMap1.put("Mobile No", mobileno);
                                    hashMap1.put("Name", patientname);
                                    hashMap1.put("EmailId", emailid);
                                    hashMap1.put("Password", password);
                                    hashMap1.put("Gender", String.valueOf(radioGroupGender));
                                    hashMap1.put("Day", day);
                                    hashMap1.put("Month", month);
                                    hashMap1.put("Year", year);


                                    FirebaseDatabase.getInstance().getReference("Patient")
                                            .child(currentUser.getUid())
                                            .setValue(hashMap1).addOnCompleteListener(task11 -> {
                                                mDialog.dismiss();

                                                AlertDialog.Builder builder1 = new AlertDialog.Builder(patient_register.this);
                                                builder1.setMessage("You Have Registered!");
                                                builder1.setCancelable(false);
                                                builder1.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
                                                AlertDialog Alert = builder1.create();
                                                Alert.show();
                                            });
                                });
                            }
                        }
                    });
                }
            }
        });

    }

    String emailpattern = getString(R.string.a_za_z0_9_a_z_a_z);
    public boolean isValid(){
        Email.setErrorEnabled(false);
        Email.setError("");
        Patientname.setErrorEnabled(false);
        Patientname.setError("");
        Pass.setErrorEnabled(false);
        Pass.setError("");

        boolean isValid,isValidname=false,isValidemail=false,isValidpassword=false;
        if(TextUtils.isEmpty(patientname)){
            TextInputLayout patient_named = findViewById(R.id.patient_name);

            patient_named.setErrorEnabled(true);
            patient_named.setError("Enter First Name");
        }else{
            isValidname = true;
        }
        if(TextUtils.isEmpty(emailid)){
            Email.setErrorEnabled(true);
            Email.setError("Email Is Required");
        }else{
            if(emailid.matches(emailpattern)){
                isValidemail = true;
            }else{
                Email.setErrorEnabled(true);
                Email.setError("Enter a Valid Email Id");
            }
        }
        if(TextUtils.isEmpty(password)){
            Pass.setErrorEnabled(true);
            Pass.setError("Enter Password");
        }else{
            if(password.length()<8){
                Pass.setErrorEnabled(true);
                Pass.setError("Password is Weak");
            }else{
                isValidpassword = true;
            }
        }
        isValid = isValidpassword && isValidemail && isValidname;
        return isValid;
    }
}