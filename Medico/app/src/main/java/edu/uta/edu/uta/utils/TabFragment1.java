package edu.uta.edu.uta.utils;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.uta.controllers.AppointmentListController;
import edu.uta.controllers.R;
import edu.uta.entities.AppointmentCard;
import edu.uta.managers.NetworkMgr;

public class TabFragment1 extends ListFragment {

    private  int patientId;
    private ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment1, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        int patientId = getArguments().getInt("patient_id");
        this.patientId = patientId;
        super.onActivityCreated(savedInstanceState);
        getAppointmentOfUser();
    }


    private Response.Listener<JSONObject> createMyReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(!response.getBoolean("error")){
                        ArrayList<AppointmentCard> list = getAppointmentList(response);
                        setListAdapter(new CardArrayAdapter(getActivity(), list));
                    }
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

    public void getAppointmentOfUser(){
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Getting all appointment...");
        pDialog.show();
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
                AppUrls.getGetAppointmentsOfUser(patientId),
                null,
                createMyReqSuccessListener(),
                createMyReqErrorListener());
        NetworkMgr.getInstance().getRequestQueue().add(myReq);
    }

    private ArrayList<AppointmentCard> getAppointmentList(JSONObject response) throws JSONException{
        JSONArray arr = response.getJSONArray("data");
        ArrayList<AppointmentCard> arrayOfAppointments = new ArrayList<AppointmentCard>();
        for(int i=0;i<arr.length();i++){
            JSONObject obj = arr.getJSONObject(i);
            String date = obj.getString("date");
            String doctor = obj.getString("doctor_id");
            String patient = obj.getString("patient_id");
            String status = obj.getString("appointment_status");
            int app_id = obj.getInt("id");
            AppointmentCard app1 = new AppointmentCard(date,doctor,patient,app_id,true);
            arrayOfAppointments.add(app1);
        }
        return arrayOfAppointments;
    }

/*
    private ArrayList<AppointmentCard> getAppointmentList(){

        ArrayList<AppointmentCard> arrayOfAppointments = new ArrayList<AppointmentCard>();
        AppointmentCard app1 = new AppointmentCard("date1","doctor1","patient1",1,true);
        AppointmentCard app2 = new AppointmentCard("date2","doctor1","patient2",2,true);
        AppointmentCard app3 = new AppointmentCard("date3","doctor1","patient3",3,true);
        arrayOfAppointments.add(app1);
        arrayOfAppointments.add(app2);
        arrayOfAppointments.add(app3);
        return arrayOfAppointments;
    }
    */
}