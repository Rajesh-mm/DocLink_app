package com.example.myproject;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class ReusableCodeForAll {
    public static void ShowAlert(patient_register patientRegister, String error, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(patientRegister);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setTitle(error).setMessage(message).show();
    }
}
