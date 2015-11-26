package edu.uta.controllers;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uta.edu.uta.utils.AppUrls;
import edu.uta.managers.NetworkMgr;

public class RegisterController extends AppCompatActivity {

    ProgressDialog pDialog;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void doRegister(View view) {
        pDialog = new ProgressDialog(this);

        pDialog.setMessage("Registering...");
        pDialog.show();
        RequestQueue queue = NetworkMgr.getInstance().getRequestQueue();

        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
                AppUrls.getGetUserUrl(1),
                null,
                createMyReqSuccessListener(),
                createMyReqErrorListener());
        queue.add(myReq);
    }


    private Response.Listener<JSONObject> createMyReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    pDialog.setMessage("done" + response.getString("username"));
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

}
