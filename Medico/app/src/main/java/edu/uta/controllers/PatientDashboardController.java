package edu.uta.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.uta.entities.MedicalRecord;

public class PatientDashboardController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);
    }

    public void showSearchGUI(View view) {
        Intent intent = new Intent(this,SearchController.class);
        startActivity(intent);
    }

    public void createAppointmentGUI(View view) {
        Intent intent = new Intent(this,AppointmentController.class);
        startActivity(intent);
    }

    public void showViewAppointmentsGUI(View view) {
        Intent intent = new Intent(this,AppointmentListController.class);
        startActivity(intent);
    }

    public void showUpdatePatientInformationGUI(View view) {
        Intent intent = new Intent(this,PatientInformationController.class);
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
