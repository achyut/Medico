package edu.uta.controllers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.uta.edu.uta.utils.AppUtils;

public class DoctorDashboardController extends AppCompatActivity {
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);
        pDialog = new ProgressDialog(this);

        /*
            int id = AppUtils.getUserFromSession(this).getId();
            pDialog.setMessage(String.valueOf(id));
            pDialog.show();
        */
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
        AppUtils.clearLogin(this);
        Intent intent = new Intent(this,LoginController.class);
        startActivity(intent);
    }
}
