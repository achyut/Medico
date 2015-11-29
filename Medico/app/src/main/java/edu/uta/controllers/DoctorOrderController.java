package edu.uta.controllers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import edu.uta.managers.NetworkMgr;

public class DoctorOrderController extends AppCompatActivity {

    ProgressDialog pDialog;
    private String appointmentid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_order);
        pDialog = new ProgressDialog(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            this.appointmentid = bundle.getString("appointment_id");
        }
    }

    public void doSave(View view){
        pDialog.setMessage("Saving Doctor order...");
        pDialog.show();
        JSONObject obj = new JSONObject(getDoctorOrderParameters());
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST,
                AppUrls.getCreateDoctorOrder(),
                obj,
                createMyReqSuccessForSaveListener(),
                createMyReqErrorListener());
        NetworkMgr.getInstance().getRequestQueue().add(myReq);

    }
    private String getValueFromTextView(int editTextId){
        EditText editText = (EditText) findViewById(editTextId);
        return editText.getText().toString().trim();
    }
    private Map<String,String> getDoctorOrderParameters() {
        Map<String, String> props = new HashMap<String, String>();
        props.put("appointment_id",this.appointmentid);
        props.put("symptoms",getValueFromTextView(R.id.doctororder_symptoms));
        props.put("diagnosis",getValueFromTextView(R.id.doctororder_diagnosis));
        props.put("medicine_prescribed",getValueFromTextView(R.id.doctororder_medicalprescribed));
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
        AlertDialog alertDialog = new AlertDialog.Builder(DoctorOrderController.this).create();
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
                Intent intent = new Intent(getApplicationContext(),DoctorDashboardController.class);
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
