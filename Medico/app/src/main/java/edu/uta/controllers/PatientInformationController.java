package edu.uta.controllers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.uta.edu.uta.utils.AppUrls;
import edu.uta.edu.uta.utils.AppUtils;
import edu.uta.managers.NetworkMgr;

public class PatientInformationController extends AppCompatActivity {

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_information);
        pDialog = new ProgressDialog(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.getBoolean("prefill")){
            prefillPatientInformation();
            bundle.putBoolean("prefill",false);
        }
    }

    private void prefillPatientInformation(){
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
                AppUrls.getGetPatientInformation(AppUtils.getUserFromSession(this).getId()),
                null,
                createPrefillSuccessListener(),
                createMyReqErrorListener());
        NetworkMgr.getInstance().getRequestQueue().add(myReq);
    }
    private Response.Listener<JSONObject> createPrefillSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    pDialog.hide();
                    if(!response.getBoolean("error")){
                        setValueToTextView(response.getString("surgical_history"), R.id.patientinfo_surgicalhistory);
                        setValueToTextView(response.getString("obstetic_history"), R.id.patientinfo_obstetrichistory);
                        setValueToTextView(response.getString("medical_allergy"), R.id.patientinfo_allergies);
                        setValueToTextView(response.getString("immunization_history"), R.id.patientinfo_immunizationhistory);
                        setValueToTextView(response.getString("family_history"), R.id.patientinfo_familyhistory);
                        setValueToTextView(response.getString("social_history"), R.id.patientinfo_socialhistory);
                        setValueToTextView(response.getString("habits"), R.id.patientinfo_habits);
                    }
                } catch (JSONException e) {
                    pDialog.setMessage(e.getMessage());
                }
            }
        };
    }

    private String getValueFromTextView(int editTextId){
        EditText editText = (EditText) findViewById(editTextId);
        return editText.getText().toString().trim();
    }

    private void setValueToTextView(String value,int editTextId){
        EditText editText = (EditText) findViewById(editTextId);
        editText.setText(value);
    }

    public void doUpdatePatientInfo(View view){
        pDialog.setMessage("Saving patient information...");
        pDialog.show();
        int userid = AppUtils.getUserFromSession(this).getId();
        JSONObject obj = new JSONObject(getForgotPasswordParameters());
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.PUT,
                AppUrls.getUpdatePatientInformation(userid),
                obj,
                createMyReqSuccessListener(),
                createMyReqErrorListener());
        NetworkMgr.getInstance().getRequestQueue().add(myReq);

    }

    private Map<String,String> getForgotPasswordParameters() {
        Map<String, String> props = new HashMap<String, String>();
        props.put("patient_id",String.valueOf(AppUtils.getUserFromSession(this).getId()));
        props.put("surgical_history", getValueFromTextView(R.id.patientinfo_surgicalhistory));
        props.put("obstetic_history", getValueFromTextView(R.id.patientinfo_obstetrichistory));
        props.put("medical_allergy", getValueFromTextView(R.id.patientinfo_allergies));
        props.put("family_history", getValueFromTextView(R.id.patientinfo_familyhistory));
        props.put("social_history", getValueFromTextView(R.id.patientinfo_socialhistory));
        props.put("habits", getValueFromTextView(R.id.patientinfo_habits));
        return props;
    }

    private Response.Listener<JSONObject> createMyReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    pDialog.hide();
                    if(response.getBoolean("error")){
                        showSuccessDialog("OOPS!!",response.getString("message"),response.getBoolean("error"));
                    }
                    else{
                        showSuccessDialog("Success!!",response.getString("message"),response.getBoolean("error"));
                    }

                } catch (JSONException e) {
                    pDialog.setMessage(e.getMessage());
                }
            }
        };
    }

    private void showSuccessDialog(String title,String message,Boolean haserr){
        AlertDialog alertDialog = new AlertDialog.Builder(PatientInformationController.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        if(haserr){
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", createErrorClickListener());
        } else{
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", createSuccessClickListener());
        }
        alertDialog.show();
    }

    private DialogInterface.OnClickListener createErrorClickListener(){
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
    }

    private DialogInterface.OnClickListener createSuccessClickListener(){
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(),PatientDashboardController.class);
                startActivity(intent);
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

}
