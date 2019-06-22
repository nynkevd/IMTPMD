package com.example.imtpmd;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;


public class MainActivity extends AppCompatActivity {
    private MedicineViewModel medicineViewModel;

    private ListView searchmatches;
    private List<String> allMedicationNames;
    private int firstId;
    private int medicationCount;

    Gson gson = new Gson();
    private AppDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // DEze database wordt ook in HomeFragment aangemaakt, dus ik weet niet of we het ergens 'globaal' kunnen doen?
         db = Room
                .databaseBuilder(getApplicationContext(), AppDatabase.class, "medicine")
                .allowMainThreadQueries() // Dit moet nog weg!!!
                .build();

        allMedicationNames = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Button naarhome = (Button) findViewById(R.id.naarhome);

        naarhome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(MainActivity.this, DashboardActivity.class));
            }
        });

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if (firstStart){
            showStartScreen();
        }

        Log.d("tessst", "tessst");

//        NetworkManager.getInstance(this).getRequest("http://136.144.230.97:8090/api/medication?api_token=CilZjPDfkHDmb29qcJkqBS7bB2cup9T7Onqcmfaqt027QhvpqBhFvLinJ6Dp", new VolleyCallback(){
//            @Override
//            public void onSuccess(String result) { //de anonieme klasse gaat nu dingen doen
////                Log.d("tessst", "HET WERKT");
//            Gson gson = new Gson();
//            testApi testpi = gson.fromJson(result, testApi.class);
//
//            Log.d("tessst", result);
//
//            }
//        });

//        NetworkManager.getInstance(getApplicationContext()).getRequest("http://136.144.230.97:8080/api/userinfo/anouk?api_token=rx7Mi675A1WDEvZPsGnrgvwkCEeOKlrX7rIPoXocluBKnupp9A02OLz7QcSL", new VolleyCallback(){
//            @Override
//            public void onSuccess(String result) {
//                medicationCount = parseInt(result);
//            }
//        });

//        insertIntoDatabase("testOchtend9", 40, 9, false);
//        insertIntoDatabase("testMiddag15", 40, 15, false);
//        insertIntoDatabase("testAvond20", 40, 20, false);
//        insertIntoDatabase("testNacht4", 40, 4, false);
//        insertIntoDatabase("testOchtend10", 40, 10, false);
//        insertIntoDatabase("testMiddag17", 40, 17, false);
//        insertIntoDatabase("testAvond22", 40, 22, false);
//        insertIntoDatabase("testNacht5", 40, 5, false);
//        insertIntoDatabase("testOchtend8", 40, 8, false);
//        insertIntoDatabase("testMiddag13", 40, 13, false);
//        insertIntoDatabase("testAvond23", 40, 23, false);
//        insertIntoDatabase("testNacht2", 40, 2, false);

    }

    private void showStartScreen() {
        Intent myIntent = new Intent(getBaseContext(), WelcomeActivity.class);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();

        startActivity(myIntent);
    }

    private void insertIntoDatabase(String name, int milligram, int time, Boolean isChecked){
        Medicine m = new Medicine(name, milligram, time, isChecked);

        medicineViewModel.insert(m);

        Log.d("Medicine", "Naar de database geschreven");
    }

    private void handleApiCall(int id){
        int callid = id;

    }


}
