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
import edu.uta.edu.uta.utils.AppUtils;
import edu.uta.managers.NetworkMgr;

public class UpdatePasswordController extends AppCompatActivity {

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
    }

    private String getValueFromTextView(int editTextId){
        EditText editText = (EditText) findViewById(editTextId);
        return editText.getText().toString();
    }

    public void doUpdatePassword(View view){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Updating new password...");
        pDialog.show();
        int userid = AppUtils.getUserFromSession(this).getId();
        JSONObject obj = new JSONObject(getUpdatePasswordParameters());
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST,
                AppUrls.getUpdatepasswordUrl(userid),
                obj,
                createMyReqSuccessListener(),
                createMyReqErrorListener());
        NetworkMgr.getInstance().getRequestQueue().add(myReq);
        //pDialog.hide();
    }

    private Map<String,String> getUpdatePasswordParameters() {
        Map<String, String> props = new HashMap<String, String>();
        props.put("oldpassword", getValueFromTextView(R.id.updatepassword_oldpassword));
        props.put("newpassword1", getValueFromTextView(R.id.updatepassword_newpass1));
        props.put("newpassword2", getValueFromTextView(R.id.updatepassword_newpass2));
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
        AlertDialog alertDialog = new AlertDialog.Builder(UpdatePasswordController.this).create();
        alertDialog.setTitle("Success!!");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", createSuccessClickListener());
        alertDialog.show();
    }

    private DialogInterface.OnClickListener createSuccessClickListener(){
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                String usertype = AppUtils.getUserFromSession(getApplication()).getUsertype();
                Intent intent;
                if(usertype.equalsIgnoreCase("doctor")){
                    intent = new Intent(getApplicationContext(),DoctorDashboardController.class);
                }
                else{
                    intent = new Intent(getApplicationContext(),PatientDashboardController.class);
                }
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
