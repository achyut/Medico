package edu.uta.edu.uta.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import edu.uta.controllers.DoctorDashboardController;
import edu.uta.controllers.LoginController;
import edu.uta.controllers.PatientDashboardController;
import edu.uta.entities.User;
import edu.uta.managers.NetworkMgr;

/**
 * Created by gaurav on 11/27/15.
 */
public class AppUtils {

    private static final String PREFS_NAME = "login";

    public static void checkIfLoggedIn(Context activity){
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
        boolean loggedIn = settings.getBoolean("logged", true);
        if(!loggedIn){
            Intent i = new Intent(activity,LoginController.class);
            activity.startActivity(i);
        }
        else{
            Intent doctorDashboard = new Intent(activity,DoctorDashboardController.class);
            Intent patientDashboard = new Intent(activity,PatientDashboardController.class);
            String usertype = getUserFromSession(activity).getUsertype();
            if(usertype!=null){
                if(usertype.equalsIgnoreCase("doctor")){
                    activity.startActivity(doctorDashboard);
                }
                else{
                    activity.startActivity(patientDashboard);
                }
            }
        }
    }


    public static void setLoggedInUser(Context context,int userid,String usertype,String username){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("userid", userid);
        editor.putString("username", username);
        editor.putString("usertype",usertype);
        editor.putBoolean("logged",true);
        editor.commit();
    }

    public static void clearLogin(Context activity){
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    public static User getUserFromSession(Context activity){
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
        User usr = new User();
        int id = settings.getInt("userid",-1);
        String username = settings.getString("username", null);
        String usertype = settings.getString("usertype",null);
        usr.setId(id);
        usr.setUsername(username);
        usr.setUsertype(usertype);
        return usr;
    }
}
