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

public class ForgotPasswordController extends AppCompatActivity {

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
    }

    private String getValueFromTextView(int editTextId){
        EditText editText = (EditText) findViewById(editTextId);
        return editText.getText().toString().trim();
    }

    public void doForgotPassword(View view){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Resetting password. Please wait...");
        pDialog.show();
        JSONObject obj = new JSONObject(getForgotPasswordParameters());
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST,
                AppUrls.getForgotpasswordUrl(),
                obj,
                createMyReqSuccessListener(),
                createMyReqErrorListener());
        NetworkMgr.getInstance().getRequestQueue().add(myReq);
    }

    private Map<String,String> getForgotPasswordParameters() {
        Map<String, String> props = new HashMap<String, String>();
        props.put("email", getValueFromTextView(R.id.forgotpassword_email));
        return props;
    }

    private Response.Listener<JSONObject> createMyReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    showSuccessDialog(response.getString("message"));
                } catch (JSONException e) {
                    pDialog.setMessage(e.getMessage());
                }
            }
        };
    }
    private void showSuccessDialog(String message){
        pDialog.hide();
        AlertDialog alertDialog = new AlertDialog.Builder(ForgotPasswordController.this).create();
        alertDialog.setTitle("Success!!");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", createSuccessClickListener());
        alertDialog.show();
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

}
