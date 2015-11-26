package edu.uta.managers;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import edu.uta.entities.User;

/**
 * Created by gaurav on 11/25/15
 */
public class RequestManager {
    private final String BASE_URL = "http://192.168.0.7:8000/";
    private String url = "";
    private String TAG = "";
    private JSONObject response;
    private VolleyError error = null;
    private Gson gson = new Gson();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public VolleyError getError() {
        return error;
    }

    public void setError(VolleyError error) {
        this.error = error;
    }

    private void setUrlAndTag(String url,String tag){
        this.setTAG(tag);
        this.setUrl(url);
    }
    public Object getResponseObject(String url,String tag, Class cls){
        setUrlAndTag(url,tag);
        doGetRequest();
        if(response!=null){
            return gson.toJson(response,cls);
        }
        else{
            return null;
        }
    }

    public List<Object> getResponseObjectList(String url,String tag, Class cls) {
        return null;
    }


    private void doGetRequest(){
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
                "http://192.168.0.7:8000/users/1",
                null,
                createMyReqSuccessListener(),
                createMyReqErrorListener());
        NetworkMgr.getInstance().getRequestQueue().add(myReq);
    }

    private void doPostRequest(){

    }

    private Response.Listener<JSONObject> createMyReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                response = res;
            }
        };
    }


    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError err) {
                error = err;
                response = null;
            }
        };
    }
    /*
    public JSONObject getResponse() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,BASE_URL+url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject res) {

//                        NetworkResponse networkResponse = error.networkResponse;
//                        Log.d(TAG,networkResponse.toString());
//
//                        Log.d(TAG, response.toString());
                        response = res;
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError err) {
                VolleyLog.d(TAG, "Error: " + err.getMessage());
                Log.e(TAG, BASE_URL, err);
                error = err;
                response = null;
                err.printStackTrace();
            }
        });

  //      NetworkMgr.getInstance().addToRequestQueue(jsonObjReq, getTAG());
        return response;
    }


    */
}
