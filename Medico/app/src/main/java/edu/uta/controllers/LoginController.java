package edu.uta.controllers;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.uta.edu.uta.utils.AppUrls;
import edu.uta.edu.uta.utils.AppUtils;
import edu.uta.entities.User;
import edu.uta.managers.NetworkMgr;

public class LoginController extends AppCompatActivity {

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        NetworkMgr.setAppContext(this);
    }

    public void showRegister(View view) {
        Intent intent = new Intent(this,RegisterController.class);
        startActivity(intent);

    }

    public void showForgotPassword(View view) {
        Intent intent = new Intent(this,ForgotPasswordController.class);
        startActivity(intent);
    }

    public void doLogin(View view) {
        checkLoginAndGetUserType();
    }

    private void checkLoginAndGetUserType(){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Signing in...");
        pDialog.show();
        RequestQueue queue = NetworkMgr.getInstance().getRequestQueue();

        JSONObject obj = new JSONObject(getLoginParameters());
        //pDialog.setMessage(getLoginParameters().toString());
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST,
                AppUrls.getLoginUrl(),
                obj,
                createMyReqSuccessListener(),
                createMyReqErrorListener());
        queue.add(myReq);
        pDialog.hide();
    }

    private Map<String,String> getLoginParameters() {
        Map<String, String> props = new HashMap<String, String>();
        props.put("username", getValueFromTextView(R.id.username_txt));
        props.put("password", getValueFromTextView(R.id.password_txt));
        return props;
    }

    private Response.Listener<JSONObject> createMyReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean loggedin = response.getBoolean("loggedin");
                    if(loggedin){
                        int userid = response.getInt("id");
                        String username = response.getString("username");
                        String usertype = response.getString("usertype");
                        AppUtils.setLoggedInUser(getApplicationContext(),userid,usertype,username);
                        showSuccessDialog(usertype);
                    }
                    else{
                        showIncorrectAlert();
                    }
//                    pDialog.setMessage("done" + response.getString("username"));
                } catch (JSONException e) {
                    pDialog.setMessage(e.getMessage());
                }
            }
        };
    }

    private void showSuccessDialog(String usertype){
        Intent doctorDashboard = new Intent(this,DoctorDashboardController.class);
        Intent patientDashboard = new Intent(this,PatientDashboardController.class);
        if(usertype!=null){
            if(usertype.equalsIgnoreCase("doctor")){
                startActivity(doctorDashboard);
            }
            else{
                startActivity(patientDashboard);
            }
        }
        else{
            showIncorrectAlert();
        }
    }

    private DialogInterface.OnClickListener createSuccessClickListener(){
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(),LoginController.class);
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
        return editText.getText().toString();

    }

    private void showIncorrectAlert(){
        AlertDialog alertDialog = new AlertDialog.Builder(LoginController.this).create();
        alertDialog.setTitle("Oops!!");
        alertDialog.setMessage("Incorrect Credentials entered.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
