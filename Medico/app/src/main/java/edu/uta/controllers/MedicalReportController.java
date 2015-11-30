package edu.uta.controllers;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import edu.uta.entities.MedicalReport;
import edu.uta.managers.NetworkMgr;

public class MedicalReportController extends AppCompatActivity {
    ProgressDialog pDialog;
    private static MedicalReport report = new MedicalReport();

    private String appointmentid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_report);
        Bundle bundle = getIntent().getExtras();
        boolean medicalRecordPage = AppUtils.getViewMedicalRecord(this);
        if(bundle!=null){
            this.appointmentid = bundle.getString("appointment_id");
        }
        checkForRefreshHide(medicalRecordPage);
        updateReport();
    }

    private void checkForRefreshHide(boolean medicalRecordPage){
        String usertypr = AppUtils.getUserFromSession(this).getUsertype();
        if(usertypr.equalsIgnoreCase("doctor")){
            if(!medicalRecordPage){
                Button btn = (Button) findViewById(R.id.medicalrecord_refresh);
                btn.setVisibility(View.VISIBLE);
                if(appointmentid!=null){
                    btn = (Button) findViewById(R.id.medicalrecord_savebutton);
                    btn.setVisibility(View.VISIBLE);
                }
                else{
                    btn = (Button) findViewById(R.id.medicalrecord_savebutton);
                    btn.setVisibility(View.GONE);
                }

            }
            else{
                hideSaveAndRefreshButton();
            }
        }
        else{
            hideSaveAndRefreshButton();
        }
    }

    private void hideSaveAndRefreshButton(){
        Button btn = (Button) findViewById(R.id.medicalrecord_refresh);
        btn.setVisibility(View.GONE);
        btn = (Button) findViewById(R.id.medicalrecord_savebutton);
        btn.setVisibility(View.GONE);
    }

    public void updateReport(){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Getting report data from device...");
        pDialog.show();
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
                AppUrls.getGetDeviceValues(),
                null,
                createMyReqSuccessListener(),
                createMyReqErrorListener());
        NetworkMgr.getInstance().getRequestQueue().add(myReq);
    }

    public void doRefresh(View view){
        updateReport();
    }

    private Response.Listener<JSONObject> createMyReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String bloodpressure = response.getString("bloodpressure");
                    String temperature = response.getString("bodytemperature");
                    String heartbeat = response.getString("heartbeat");
                    String airflow = response.getString("airflow");
                    String bloodsugerlevel = response.getString("bloodsugerlevel");

                    report.setAirflow(airflow);
                    report.setBloodpressure(bloodpressure);
                    report.setBloodsugerlevel(bloodsugerlevel);
                    report.setHeartbeat(heartbeat);
                    report.setTemperature(temperature);

                    setTextReportValues();

                } catch (JSONException e) {
                    pDialog.setMessage(e.getMessage());
                }
            }
        };
    }

    public void doSave(View view){
        pDialog.setMessage("Saving medical report information...");
        pDialog.show();
        JSONObject obj = new JSONObject(getCreateReportParameters());
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST,
                AppUrls.getSaveReport(),
                obj,
                createMyReqSuccessForSaveListener(),
                createMyReqErrorListener());
        NetworkMgr.getInstance().getRequestQueue().add(myReq);

    }

    private Map<String,String> getCreateReportParameters() {
        Map<String, String> props = new HashMap<String, String>();
        props.put("appointment_id",this.appointmentid);
        props.put("bloodpressure",report.getBloodpressure());
        props.put("bodytemperature",report.getTemperature());
        props.put("heartbeat",report.getHeartbeat());
        props.put("bloodsugerlevel", report.getBloodsugerlevel());
        props.put("airflow", report.getAirflow());
        return props;
    }

    private Response.Listener<JSONObject> createMyReqSuccessForSaveListener() {
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
        AlertDialog alertDialog = new AlertDialog.Builder(MedicalReportController.this).create();
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
               // Intent intent = new Intent(getApplicationContext(),DoctorDashboardController.class);
               // startActivity(intent);
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


    public void setTextReportValues(){
        setValueToTextView(report.getAirflow(),R.id.medicalreport_airflow);
        setValueToTextView(report.getBloodpressure(),R.id.medicalreport_bloodpressure);
        setValueToTextView(report.getBloodsugerlevel(),R.id.medicalreport_bloodsuger);
        setValueToTextView(report.getHeartbeat(),R.id.medicalreport_heartbeat);
        setValueToTextView(report.getTemperature(), R.id.medicalreport_temperature);
        pDialog.hide();
    }

    private void setValueToTextView(String value,int editTextId){
        TextView textView = (TextView)findViewById(editTextId);
        textView.setText(value);
    }
}
