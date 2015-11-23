package edu.uta.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DoctorDashboardController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);
    }

    public void showSearchGUI(View view) {
        Intent intent = new Intent(this,SearchController.class);
        startActivity(intent);
    }

    public void showAppointmentScheduledGUI(View view) {
        Intent intent = new Intent(this,DoctorOrderController.class);
        startActivity(intent);
    }

    public void showNewMedicalReport(View view) {
        Intent intent = new Intent(this,MedicalReportController.class);
        startActivity(intent);
    }

    public void showMedicalRecordGUI(View view) {
        Intent intent = new Intent(this,MedicalRecordController.class);
        startActivity(intent);
    }
    public void showUpdatePasswordGUI(View view) {
        Intent intent = new Intent(this,UpdatePasswordController.class);
        startActivity(intent);
    }

    public void doLogout(View view) {
        Intent intent = new Intent(this,LoginController.class);
        startActivity(intent);
    }
}
