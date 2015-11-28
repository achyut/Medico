package edu.uta.controllers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uta.edu.uta.utils.AppUrls;
import edu.uta.edu.uta.utils.AppUtils;
import edu.uta.edu.uta.utils.AppointmentUtils;
import edu.uta.entities.MedicalRecord;
import edu.uta.managers.NetworkMgr;

public class PatientDashboardController extends AppCompatActivity {

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);
        checkForPatientInformation(AppUtils.getUserFromSession(this).getId());
    }

    public void checkForPatientInformation(int userid){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Checking patient information...");
        pDialog.show();
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
                AppUrls.getCheckForPatientInformation(userid),
                null,
                createMyReqSuccessListener(),
                createMyReqErrorListener());
        NetworkMgr.getInstance().getRequestQueue().add(myReq);
    }

    private Response.Listener<JSONObject> createMyReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    pDialog.hide();
                    if(response.getBoolean("isempty")){
                        Intent intent = new Intent(getApplicationContext(),PatientInformationController.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    pDialog.setMessage(e.getMessage());
                }
            }
        };
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.setMessage(error.getMessage());
            }
        };
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
/*

        Intent intent = new Intent(this,AppointmentListController.class);
        startActivity(intent);
*/


        AppointmentUtils appointmentUtils = new AppointmentUtils(this);
        appointmentUtils.getAppointmentOfUser();

    }

    public void showUpdatePatientInformationGUI(View view) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("prefill",true);
        Intent intent = new Intent(this,PatientInformationController.class);
        intent.putExtras(bundle);
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
