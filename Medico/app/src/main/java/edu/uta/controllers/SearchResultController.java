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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.uta.edu.uta.utils.AppUtils;
import edu.uta.edu.uta.utils.CardArrayAdapter;
import edu.uta.entities.AppointmentCard;

public class SearchResultController extends ListActivity {

    ArrayList<String> list = new ArrayList<String>();
    HashMap<String,Integer> map;
    ProgressDialog pDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pDialog = new ProgressDialog(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String str = bundle.getString("patients");
            Gson gson = new Gson();
            Type listOfTestObject = new TypeToken<HashMap<String,Integer>>(){}.getType();
            map = gson.fromJson(str, listOfTestObject);
            for(Map.Entry<String,Integer> obj :map.entrySet() ){
                list.add(obj.getKey());
            }

        }
        setListAdapter(new ArrayAdapter<String>(this, R.layout.single_search_result,list));

        ListView listView = getListView();
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String patientname = ((TextView) view).getText().toString();
                //Toast.makeText(getApplicationContext(),patientname, Toast.LENGTH_SHORT).show();
                Integer patientId = map.get(patientname);
                Bundle bundle = new Bundle();
                bundle.putInt("patient_id",patientId);
                Intent intent = new Intent(getApplicationContext(),MedicalRecordController.class);
                intent.putExtras(bundle);
                AppUtils.setViewMedicalRecord(getApplicationContext(),true);
                startActivity(intent);

                // When clicked, show a toast with the TextView text
                //Toast.makeText(getApplicationContext(),((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
