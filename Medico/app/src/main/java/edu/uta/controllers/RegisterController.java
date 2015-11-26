package edu.uta.controllers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.uta.edu.uta.utils.AppUrls;
import edu.uta.entities.User;
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
        pDialog.hide();
    }

    private Response.Listener<JSONObject> createMyReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                showSuccessDialog();
                /*
                try {
                    pDialog.setMessage("done" + response.getString("username"));

                } catch (JSONException e) {
                    pDialog.setMessage(e.getMessage());
                }*/
            }
        };
    }

    private void showSuccessDialog(){
            AlertDialog alertDialog = new AlertDialog.Builder(RegisterController.this).create();
            alertDialog.setTitle("Success!!");
            alertDialog.setMessage("User successfully registered. Please login to continue.");
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

    private Map<String,String> getRegistrationParameters(){
        Map<String,String> props = new HashMap<String,String>();
        props.put("username",getValueFromTextView(R.id.username_textfield));
        props.put("address",getValueFromTextView(R.id.address_textfield));
        props.put("phone",getValueFromTextView(R.id.phone_textfield));
        props.put("fullname",getValueFromTextView(R.id.fullname_textfield));
        props.put("dateofbirth",getValueFromTextView(R.id.dateofbirth_textfield));
        props.put("ssn",getValueFromTextView(R.id.ssn_textfield));
        props.put("gender",getGender());
        props.put("email",getValueFromTextView(R.id.email_textfield));
        props.put("usertype",getUserType());
        props.put("familydocotorname",getValueFromTextView(R.id.familydoctorname_textfield));
        return props;
    }

    private String getGender(){
        RadioGroup radioGenderGroup = (RadioGroup) findViewById(R.id.gender_radiogroup);
        return "Male";
    }

    private String getUserType(){
        return "Patient";
    }

    private String getValueFromTextView(int editTextId){
        EditText editText = (EditText) findViewById(editTextId);
        return editText.getText().toString();

    }
}
