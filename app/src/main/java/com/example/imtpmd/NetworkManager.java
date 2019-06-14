package com.example.imtpmd;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class NetworkManager {
    private static final String TAG = "NetworkManagerERROR";
    private static NetworkManager instance = null;

    public RequestQueue requestQueue;

    public NetworkManager(Context context){
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized NetworkManager getInstance(Context context){
        if(instance == null){
            instance = new NetworkManager(context);
        }

        return instance;
    }

    public static synchronized NetworkManager getInstance(){
        if(instance == null){
            throw new IllegalStateException("Hij betsaat niet, eerst getinstance met context aanroepen");
        }
        return instance;
    }

    public void getRequest(String prefixUrl, final VolleyCallback volleyCallback){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, prefixUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // void kan niet returnen, dus hij kan het niet naar mainactivity terugsturen
                volleyCallback.onSuccess(response);
            }
        }, new Response.ErrorListener() { // Als er iets mis gaat wordt er een error gegeven
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "oeps");
                Log.d(TAG, error.toString());
            }
        }); //als het misgaat wordt dit uitgevoerd

        requestQueue.add(stringRequest);

    }
}
