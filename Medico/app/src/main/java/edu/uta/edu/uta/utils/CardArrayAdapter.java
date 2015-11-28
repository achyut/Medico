package edu.uta.edu.uta.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.uta.controllers.AppointmentDetailsController;
import edu.uta.controllers.AppointmentListController;
import edu.uta.controllers.PatientDashboardController;
import edu.uta.controllers.PatientInformationController;
import edu.uta.controllers.R;
import edu.uta.entities.Appointment;
import edu.uta.entities.AppointmentCard;
import edu.uta.managers.NetworkMgr;

/**
 * Created by gaurav on 11/28/15.
 */
public class CardArrayAdapter  extends ArrayAdapter<AppointmentCard> {

    ProgressDialog pDialog;
    private int pos;
    private Context context;
    private ArrayList<AppointmentCard> values;

    public CardArrayAdapter(Context context, ArrayList<AppointmentCard> values) {
        super(context, R.layout.activity_appointment_list, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_appointment_list, parent, false);
        AppointmentCard app = values.get(position);


        TextView date = (TextView) rowView.findViewById(R.id.appointment_highlight);
        TextView doctor = (TextView) rowView.findViewById(R.id.appointment_doctorname);

        Button viewdetailbutton = (Button) rowView.findViewById(R.id.appointmentlist_viewdetails);
        Button cancelbutton = (Button) rowView.findViewById(R.id.appointmentlist_cancel);

        date.setText("Appointment for date: " + app.getDate());
        doctor.setText("Doctor name: " + app.getDoctorname());


        viewdetailbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = position;
                AppointmentCard appcard = values.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("appointment_id", appcard.getAppointmentid());
                Intent intent = new Intent(context, AppointmentDetailsController.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }

        });

        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = position;
                showDeleteDialog();
            }

        });

        return rowView;
    }



    private void showDeleteDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Are you sure?");
        alertDialog.setMessage("Do you want to delete this appointment?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", createSuccessClickListener());
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", createErrorClickListener());
        alertDialog.show();
    }

    private DialogInterface.OnClickListener createErrorClickListener(){
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
    }

    private DialogInterface.OnClickListener createSuccessClickListener(){
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                deleteAppointment();
            }
        };
    }

    private void deleteAppointment(){

        AppointmentCard appcard = values.get(pos);

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Deleting appointment...");
      //  pDialog.setMessage(String.valueOf(appcard.getAppointmentid()));
        pDialog.show();

        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.DELETE,
                AppUrls.getDeleteAppointment(appcard.getAppointmentid()),
                null,
                createMyReqSuccessListener(),
                createMyReqErrorListener());
        NetworkMgr.getInstance().getRequestQueue().add(myReq);

    }

    private Response.Listener<JSONObject> createMyReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //pDialog.hide();
                    showDeleteSuccessDialog();
                    if(!response.getBoolean("error")){
                        Intent intent = new Intent(context,AppointmentListController.class);
                        context.startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(context,PatientDashboardController.class);
                        context.startActivity(intent);
                    }
                } catch (JSONException e) {
                    pDialog.setMessage(e.getMessage());
                }
            }
        };
    }

    private void showDeleteSuccessDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Success!!");
        alertDialog.setMessage("Appointment Successfully Deleted.");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", createDeleteSuccessClickListener());
        alertDialog.show();
    }


    private DialogInterface.OnClickListener createDeleteSuccessClickListener(){
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
}
