package com.example.imtpmd;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import static java.lang.Integer.parseInt;

public class StartupActivity extends AppCompatActivity {

    private Intent i;
    private int medicationCount;
    Gson gson = new Gson();
    private MedicationNameViewModel medicationNameViewModel;
    public Boolean allowed = true;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_startup);

        medicationNameViewModel = ViewModelProviders.of(this).get(MedicationNameViewModel.class);

        if (isNetworkAvailable() && allowed){
            insertMedicationNamesFromApiIntoDatabase();
            allowed = false;
        }

        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if (firstStart){
            new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {
                  Intent myIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                  SharedPreferences.Editor editor = prefs.edit();
                  editor.putBoolean("firstStart", false);
                  editor.apply();

                  StartupActivity.this.startActivity(myIntent);
                  StartupActivity.this.finish();
              }
          }, 1000);


        } else if (!firstStart){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent myIntent = new Intent(getApplicationContext(), DashboardActivity.class);
                    StartupActivity.this.startActivity(myIntent);
                    StartupActivity.this.finish();
                }
            }, 1000);
        }
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void insertMedicationNamesFromApiIntoDatabase(){
        NetworkManager.getInstance(getApplicationContext()).getRequest("http://136.144.230.97:8090/api/medicationcount?api_token=CilZjPDfkHDmb29qcJkqBS7bB2cup9T7Onqcmfaqt027QhvpqBhFvLinJ6Dp", new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                medicationCount = parseInt(result);
                MedicationCount c = new MedicationCount(medicationCount);
//                db.medicationCountDAO().insertAll(c);

                for (int i = 1; i <= medicationCount; i++) {
                    NetworkManager.getInstance(getApplicationContext()).getRequest("http://136.144.230.97:8090/api/medicationname/" + String.valueOf(i) + "?api_token=CilZjPDfkHDmb29qcJkqBS7bB2cup9T7Onqcmfaqt027QhvpqBhFvLinJ6Dp", new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) { //de anonieme klasse gaat nu dingen doen
                            MedicationName apicall = gson.fromJson(result, MedicationName.class);
                            MedicationName mn = new MedicationName(apicall.getName());
                            medicationNameViewModel.insert(mn);
                        }
                    });
                }
            }
        });
    }
}
