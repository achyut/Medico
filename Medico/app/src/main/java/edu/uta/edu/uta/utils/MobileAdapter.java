package edu.uta.edu.uta.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.uta.controllers.R;
import edu.uta.entities.AppointmentCard;

/**
 * Created by gaurav on 11/28/15.
 */

public class MobileAdapter extends ArrayAdapter<AppointmentCard> {
    private Context context;
    private ArrayList<AppointmentCard> values;

    public MobileAdapter(Context context, ArrayList<AppointmentCard> values) {
        super(context, R.layout.activity_appointment_list, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_appointment_list, parent, false);
        TextView date = (TextView) rowView.findViewById(R.id.appointment_highlight);
        TextView doctor = (TextView) rowView.findViewById(R.id.appointment_doctorname);
        AppointmentCard app = values.get(position);
        date.setText("Appointment for date: " + app.getDate());
        doctor.setText("Doctor name: " + app.getDoctorname());
        return rowView;
    }
}
