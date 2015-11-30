package edu.uta.edu.uta.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.uta.controllers.R;
import edu.uta.managers.NetworkMgr;

public class TabFragment2 extends Fragment {
    ProgressDialog pDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        int patientId = getArguments().getInt("patient_id");
        updateReport(patientId);
        super.onActivityCreated(savedInstanceState);
    }

    public void updateReport(int userid){
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Getting patient information..");
        pDialog.show();
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
                AppUrls.getGetPatientInformation(userid),
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
                    String surgical_history = "";
                    String obstetic_history = "";
                    String immunization_history = "";
                    String medical_allergy = "";
                    String family_history = "";
                    String social_history = "";
                    String habits = "";

                    if(!response.getBoolean("error")){
                         surgical_history = response.getString("surgical_history");
                         obstetic_history = response.getString("obstetic_history");
                         immunization_history = response.getString("immunization_history");
                         medical_allergy = response.getString("medical_allergy");
                         family_history = response.getString("family_history");
                         social_history = response.getString("social_history");
                         habits = response.getString("habits");
                    }
                    setValueToTextView(surgical_history,R.id.tabfragment2_surgicalhistory);
                    setValueToTextView(obstetic_history,R.id.tabfragment2_obstetrichistory);
                    setValueToTextView(immunization_history,R.id.tabfragment2_immunizationhistory);
                    setValueToTextView(medical_allergy,R.id.tabfragment2_allergy);
                    setValueToTextView(family_history, R.id.tabfragment2_familyhistory);
                    setValueToTextView(social_history, R.id.tabfragment2_socialhistory);
                    setValueToTextView(habits, R.id.tabfragment2_habits);
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
        TextView textView = (TextView)getActivity().findViewById(editTextId);
        textView.setText(value);
    }

}