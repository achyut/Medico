package edu.uta.controllers;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import edu.uta.edu.uta.utils.AppUrls;
import edu.uta.edu.uta.utils.AppUtils;
import edu.uta.edu.uta.utils.CardArrayAdapter;
import edu.uta.edu.uta.utils.MobileAdapter;
import edu.uta.entities.AppointmentCard;
import edu.uta.managers.NetworkMgr;

public class AppointmentListController extends ListActivity {

    ProgressDialog pDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String str = bundle.getString("appointments");
            Gson gson = new Gson();
            Type listOfTestObject = new TypeToken<ArrayList<AppointmentCard>>(){}.getType();
            ArrayList<AppointmentCard> list = gson.fromJson(str, listOfTestObject);
            pDialog = new ProgressDialog(this);
            //pDialog.setMessage(list.toString());
            //pDialog.show();
            setListAdapter(new CardArrayAdapter(this, list));
        }
        //setListAdapter(new MobileAdapter(this,getAppointmentList()));


    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //get selected items
        String selectedValue = (String) getListAdapter().getItem(position);
        Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();

    }

    /*
    ProgressDialog pDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        *//*Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String str = bundle.getString("appointments");

            Gson gson = new Gson();
            Type listOfTestObject = new TypeToken<ArrayList<AppointmentCard>>(){}.getType();
            ArrayList<AppointmentCard> list = gson.fromJson(str, listOfTestObject);
            pDialog = new ProgressDialog(this);
            pDialog.setMessage(list.toString());
            pDialog.show();
        }
*//*
        super.onCreate(savedInstanceState);

        setListAdapter(new MobileAdapter(getApplicationContext(), getAppointmentList()));
*//*

        ListView listView = getListView();
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
*//*

    }*/

    private ArrayList<AppointmentCard> getAppointmentList(){

        ArrayList<AppointmentCard> arrayOfAppointments = new ArrayList<AppointmentCard>();
        AppointmentCard app1 = new AppointmentCard("date1","doctor1",1,true);
        AppointmentCard app2 = new AppointmentCard("date2","doctor1",2,true);
        AppointmentCard app3 = new AppointmentCard("date3","doctor1",3,true);
        arrayOfAppointments.add(app1);
        arrayOfAppointments.add(app2);
        arrayOfAppointments.add(app3);
        return arrayOfAppointments;
    }

  /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);
    }*/
}
