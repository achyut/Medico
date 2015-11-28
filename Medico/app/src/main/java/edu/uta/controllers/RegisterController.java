package edu.uta.controllers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.uta.edu.uta.utils.AppUrls;
import edu.uta.edu.uta.utils.AppUtils;
import edu.uta.entities.User;
import edu.uta.managers.NetworkMgr;

public class RegisterController extends AppCompatActivity {

    ProgressDialog pDialog;
    Gson gson = new Gson();
    private String gnd = "Male";
    private String usrtype = "Patient";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        RadioGroup radioGenderGroup = (RadioGroup) findViewById(R.id.usertype_radiogroup);
        radioGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.doctor_radio:
                        setUserType("Doctor");
                        break;
                    case R.id.patient_radio:
                        setUserType("Patient");
                        break;
                }
            }
        });


        RadioGroup radioGenderGroup1 = (RadioGroup) findViewById(R.id.gender_radiogroup);
        radioGenderGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male_radio:
                        setGnd("Male");
                        break;
                    case R.id.female_radio:
                        setGnd("Female");
                        break;
                }
            }
        });
    }

    public void doRegister(View view) {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Registering...");
        pDialog.show();
        RequestQueue queue = NetworkMgr.getInstance().getRequestQueue();

        JSONObject obj = new JSONObject(getRegistrationParameters());
        //pDialog.setMessage(getRegistrationParameters().toString());
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST,
                AppUrls.getRegisterUrl(),
                obj,
                createMyReqSuccessListener(),
                createMyReqErrorListener());
        queue.add(myReq);
    }

    private Response.Listener<JSONObject> createMyReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //pDialog.setMessage(response.toString());
                    pDialog.hide();
                    if(response.getBoolean("error")){
                        showSuccessDialog(response.getString("message"),"OOPs!!",false);
                    }
                    else{
                        showSuccessDialog(response.getString("message"),"Success!!",true);
                    }

                } catch (JSONException e) {
                    pDialog.setMessage(e.getMessage());
                }
            }
        };
    }

    private void showSuccessDialog(String message,String title,Boolean isError){
            AlertDialog alertDialog = new AlertDialog.Builder(RegisterController.this).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            if(isError){
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", createSuccessClickListener());
            }
            else{
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", createErrorClickListener());

            }
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

    private Map<String,String> getRegistrationParameters(){
        Map<String,String> props = new HashMap<String,String>();

        props.put("username",getValueFromTextView(R.id.username_textfield));
        props.put("address",getValueFromTextView(R.id.address_textfield));
        props.put("password",getValueFromTextView(R.id.password_textfield));
        props.put("phone",getValueFromTextView(R.id.phone_textfield));
        props.put("fullname",getValueFromTextView(R.id.fullname_textfield));
        props.put("dateofbirth",getValueFromTextView(R.id.dateofbirth_textfield));
        props.put("ssn",getValueFromTextView(R.id.ssn_textfield));
        props.put("gender",getGender());
        props.put("email",getValueFromTextView(R.id.email_textfield));
        props.put("usertype",getUserType());
        props.put("familydocotorname", getValueFromTextView(R.id.familydoctorname_textfield));

        return props;
    }

    private String getGender(){
        return this.gnd;
    }

    private void setUserType(String userType){
        this.usrtype = userType;
    }
    private String getUserType(){
        return this.usrtype;
    }

    private void setGnd(String gnd){
        this.gnd = gnd;
    }

    private String getValueFromTextView(int editTextId){
        EditText editText = (EditText) findViewById(editTextId);
        return editText.getText().toString().trim();
    }
}
