package edu.uta.controllers;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uta.edu.uta.utils.AppUrls;
import edu.uta.managers.NetworkMgr;

public class DoctorOrderDetialsController extends AppCompatActivity {

    ProgressDialog pDialog;
    private String appointment_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctororder_details);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            appointment_id = bundle.getString("appointment_id");

        }
        updateReport(appointment_id);
    }


    public void updateReport(String appointmentId){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Getting doctor order details..");
        pDialog.show();
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
                AppUrls.getGetDoctorOrder(appointmentId),
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

                    String symptoms = "";
                    String diagnosis = "";
                    String medicine_prescribed = "";
                    if(!response.getBoolean("error")){
                        symptoms = response.getString("symptoms");
                        diagnosis = response.getString("diagnosis");
                        medicine_prescribed = response.getString("medicine_prescribed");
                    }
                    setValueToTextView(symptoms,R.id.doctor_orderdetials_symptoms);
                    setValueToTextView(diagnosis,R.id.doctor_orderdetials_diagnosis);
                    setValueToTextView(medicine_prescribed,R.id.doctor_orderdetails_medicineprescribed);

                    pDialog.hide();
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

    private void setValueToTextView(String value,int editTextId){
        TextView textView = (TextView) findViewById(editTextId);
        textView.setText(value);
    }

}
