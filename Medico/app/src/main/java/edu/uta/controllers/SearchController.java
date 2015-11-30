package edu.uta.controllers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.uta.edu.uta.utils.AppUrls;
import edu.uta.entities.AppointmentCard;
import edu.uta.managers.NetworkMgr;

public class SearchController extends AppCompatActivity {
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void doSearch(View view){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Searching patient...");
        pDialog.show();
        JSONObject obj = new JSONObject(getSearchParameters());
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST,
                AppUrls.getSearchUrl(),
                obj,
                createMyReqSuccessForSaveListener(),
                createMyReqErrorListener());
        NetworkMgr.getInstance().getRequestQueue().add(myReq);
    }

    private Response.Listener<JSONObject> createMyReqSuccessForSaveListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("error")){
                        showNotFoundDialog("OOPS!!", response.getString("message"));
                    }
                    else{
                        Gson gson = new Gson();
                        String val = gson.toJson(getPatientList(response));
                        // pDialog.setMessage(val);
                        Bundle bundle = new Bundle();
                        bundle.putString("patients", val);
                        Intent intent = new Intent(getApplicationContext(),SearchResultController.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    pDialog.hide();
                } catch (JSONException e) {
                    pDialog.setMessage(e.getMessage());
                }
            }
        };
    }

    private HashMap<String,Integer> getPatientList(JSONObject response) throws JSONException{
        JSONArray arr = response.getJSONArray("data");
        HashMap<String,Integer> map = new HashMap<String,Integer>();
        for(int i=0;i<arr.length();i++){
            JSONObject obj = arr.getJSONObject(i);
            Integer patient_id = obj.getInt("id");
            String patient_name = obj.getString("fullname");
            map.put(patient_name,patient_id);
        }
        return map;
    }

    private void showNotFoundDialog(String title,String message){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", createErrorClickListener());
        alertDialog.show();
    }
    private DialogInterface.OnClickListener createErrorClickListener(){
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
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

    private Map<String,String> getSearchParameters() {
        Map<String, String> props = new HashMap<String, String>();
        props.put("patient_name",getValueFromTextView(R.id.search_patientname));
        props.put("patient_ssn",getValueFromTextView(R.id.search_ssn));
        props.put("medical_record_id",getValueFromTextView(R.id.search_medicalrecordid));
        props.put("patient_phone",getValueFromTextView(R.id.search_patient_phone));
        return props;
    }
    private String getValueFromTextView(int editTextId){
        EditText editText = (EditText) findViewById(editTextId);
        return editText.getText().toString().trim();
    }
}
