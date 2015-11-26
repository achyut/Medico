package edu.uta.controllers;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.app.AlertDialog;
import java.util.HashMap;
import java.util.Map;

import edu.uta.managers.NetworkMgr;

public class LoginController extends AppCompatActivity {

    private Map<String,String> users = new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fillUsers();
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
        Intent doctorDashboard = new Intent(this,DoctorDashboardController.class);
        Intent patientDashboard = new Intent(this,PatientDashboardController.class);

        String username = getValueFromTextView(R.id.username_txt);
        String password = getValueFromTextView(R.id.password_txt);

        Bundle bundle1 = storeInBundle(username, password);


        String usertype = getUserType(username);
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

    private Bundle storeInBundle(String username, String password) {
        Bundle bundle = new Bundle();
        bundle.putString("username",username);
        bundle.putString("password",password);
        return bundle;
    }

    private String getUserType(String username){
        return users.get(username);
    }

    private void fillUsers(){
        users.put("doctor","doctor");
        users.put("patient","patient");
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
