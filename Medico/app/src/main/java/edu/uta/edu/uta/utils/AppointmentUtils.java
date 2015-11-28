package edu.uta.edu.uta.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
import edu.uta.controllers.PatientInformationController;
import edu.uta.entities.AppointmentCard;
import edu.uta.managers.NetworkMgr;

/**
 * Created by gaurav on 11/28/15.
 */
public class AppointmentUtils {

    Context context;
    ProgressDialog pDialog;

    public AppointmentUtils(Context context) {
        this.context = context;
    }

    private Response.Listener<JSONObject> createMyReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(!response.getBoolean("error")){
                        Gson gson = new Gson();
                        String val = gson.toJson(getAppointmentList(response));
                       // pDialog.setMessage(val);
                        Bundle bundle = new Bundle();
                        bundle.putString("appointments", val);
                        Intent intent = new Intent(context, AppointmentListController.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                        pDialog.hide();
                    }
                    else{
                        pDialog.setMessage(response.getString("message"));
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
    private String getUrl(){
        if(AppUtils.getUserFromSession(context).getUsertype().equalsIgnoreCase("doctor")){
            return AppUrls.getGetAppointmentsOfDoctor(AppUtils.getUserFromSession(context).getId());
        }
        else{
            return AppUrls.getGetAppointmentsOfUser(AppUtils.getUserFromSession(context).getId());
        }

    }
    public void getAppointmentOfUser(){
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Getting all appointment...");
        pDialog.show();
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
                getUrl(),
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
            String status = obj.getString("appointment_status");
            int app_id = obj.getInt("id");
            AppointmentCard app1 = new AppointmentCard(date,doctor,app_id,true);
            arrayOfAppointments.add(app1);
        }
        return arrayOfAppointments;
    }

}
