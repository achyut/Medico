package edu.uta.controllers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import edu.uta.edu.uta.utils.AppUrls;
import edu.uta.edu.uta.utils.AppUtils;
import edu.uta.edu.uta.utils.CardArrayAdapter;
import edu.uta.entities.AppointmentCard;

public class AppointmentDetailsController extends AppCompatActivity {

    private String appointmentid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            this.appointmentid = bundle.getString("appointment_id");
            setValueToTextView(bundle.getString("date"), R.id.appointmentdetails_date);
            setValueToTextView(bundle.getString("doctor"),R.id.appointmentdetails_doctor);
            setValueToTextView(bundle.getString("appointment_status"),R.id.appointmentdetails_status);
            setValueToTextView(bundle.getString("chief_complaint"),R.id.appointmentdetails_chiefcomplaint);
            setValueToTextView(bundle.getString("summary_of_illness"),R.id.appointmentdetails_summaryofillness);
            setValueToTextView(bundle.getString("physical_examination"),R.id.appointmentdetails_physicalexamination);
            setValueToTextView(bundle.getString("assessment"), R.id.appointmentdetails_assessment);
        }
        String usertypr = AppUtils.getUserFromSession(this).getUsertype();
        if(usertypr.equalsIgnoreCase("patient")){
            Button btn = (Button) findViewById(R.id.appointmentdetails_doctorbutton);
            btn.setVisibility(View.GONE);
            btn = (Button) findViewById(R.id.appointmentdetails_reportbutton);
            btn.setVisibility(View.GONE);
        }
        else{
            Button btn = (Button) findViewById(R.id.appointmentdetails_doctorbutton);
            btn.setVisibility(View.VISIBLE);
            btn = (Button) findViewById(R.id.appointmentdetails_reportbutton);
            btn.setVisibility(View.VISIBLE);
        }
    }

    private void setValueToTextView(String value,int editTextId){
        TextView textView = (TextView) findViewById(editTextId);
        textView.setText(value);
    }

    public void doCreateReport(View view){
        Bundle bundle = new Bundle();
        bundle.putString("appointment_id",this.appointmentid);
        Intent intent = new Intent(this,MedicalReportController.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void doCreateDoctorOrder(View view){
        Bundle bundle = new Bundle();
        bundle.putString("appointment_id",this.appointmentid);
        Intent intent = new Intent(this,DoctorOrderController.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
