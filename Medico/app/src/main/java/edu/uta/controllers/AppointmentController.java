package edu.uta.controllers;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.uta.edu.uta.utils.AppUrls;
import edu.uta.edu.uta.utils.AppUtils;
import edu.uta.managers.NetworkMgr;

public class AppointmentController extends AppCompatActivity {

    ProgressDialog pDialog;
    Map<String,Integer> doctors = new HashMap<String,Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);
        prefillDoctors();
        setDefaultDate();
    }

    private void setDefaultDate(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        String dt = format.format(date);
        setValueToTextView(dt, R.id.create_appointment_date);
    }
    private void prefillDoctors(){

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Getting available doctors...");
        pDialog.show();

        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
                AppUrls.getGetAllDoctors(),
                null,
                createPrefillSuccessListener(),
                createMyReqErrorListener());
        NetworkMgr.getInstance().getRequestQueue().add(myReq);
    }

    private void setDataInSpinner() {
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_doctor);
        List list = new ArrayList();
        for(Map.Entry ent : doctors.entrySet()){
            list.add(ent.getKey());
        }
        ArrayAdapter dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }

    private Response.Listener<JSONObject> createPrefillSuccessListener() {
        return new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    pDialog.hide();
                    JSONArray arr = response.getJSONArray("result");
                    if(arr.length()>0){
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject childJSONObject = arr.getJSONObject(i);
                            String name = childJSONObject.getString("name");
                            int id = childJSONObject.getInt("id");
                            doctors.put(name,id);
                        }
                        setDataInSpinner();
                    }
                    else{
                        pDialog.setMessage("Doctors not found to make appointment");
                        pDialog.show();
                    }

                } catch (JSONException e) {
                    pDialog.setMessage(e.getMessage());
                }
            }
        };
    }

    public void doCreateNewAppointment(View view){
        pDialog.setMessage("Creating new appointment...");
        pDialog.show();
        JSONObject obj = new JSONObject(getNewAppointmentParameters());
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST,
                AppUrls.getCreateaNewAppointment(),
                obj,
                createMyReqSuccessListener(),
                createMyReqErrorListener());
        NetworkMgr.getInstance().getRequestQueue().add(myReq);

    }

    private Map<String,String> getNewAppointmentParameters() {
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_doctor);
        String doctorname = spinner2.getSelectedItem().toString();
        Map<String, String> props = new HashMap<String, String>();
        props.put("patient_id",String.valueOf(AppUtils.getUserFromSession(this).getId()));
        props.put("doctor_id",doctors.get(doctorname).toString());
        props.put("appointment_status","active");
        props.put("date", getValueFromTextView(R.id.create_appointment_date));
        props.put("chief_complaint", getValueFromTextView(R.id.create_appointment_chiefcomplaint));
        props.put("summary_of_illness", getValueFromTextView(R.id.create_appointment_summaryofillness));
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
        AlertDialog alertDialog = new AlertDialog.Builder(AppointmentController.this).create();
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

    private String getValueFromTextView(int editTextId){
        EditText editText = (EditText) findViewById(editTextId);
        return editText.getText().toString().trim();
    }

    private void setValueToTextView(String value,int editTextId){
        EditText editText = (EditText) findViewById(editTextId);
        editText.setText(value);
    }

}
