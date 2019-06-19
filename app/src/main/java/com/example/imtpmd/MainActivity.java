package com.example.imtpmd;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView searchmatches;
    private List<MedicationName> allMedicationNames;

    Gson gson = new Gson();
    private AppDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // DEze database wordt ook in HomeFragment aangemaakt, dus ik weet niet of we het ergens 'globaal' kunnen doen?
         db = Room
                .databaseBuilder(getApplicationContext(), AppDatabase.class, "medicine")
                .allowMainThreadQueries() // Dit moet nog weg!!!
                .build();

        allMedicationNames = new ArrayList<MedicationName>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ListView medsList = findViewById(R.id.medslistview);
//
//        insertIntoDatabase("ochtendMed", 40, 9, true);
//        insertIntoDatabase("middagMed", 40, 15, false);
//        insertIntoDatabase("avondMed", 40, 21, true);
//        insertIntoDatabase("test", 100, 10, false);

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

        //Database vullen
        db.medicationNameDAO().deleteAll();
        for (int i = 1; i <= 25; i++){
            this.handleApiCall(i);
        }
    }

    private void showStartScreen() {
        Intent myIntent = new Intent(getBaseContext(), WelcomeActivity.class);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();

        startActivity(myIntent);
    }

//    private void findMatches(String search){
//        TextView searchmatches = (TextView)findViewById(R.id.matchPills);
//        searchmatches.setText("");
//
//        for(int i = 0; i < meds.size() ; i++){
//            if (meds.get(i).startsWith(search)){
//                searchmatches.append(meds.get(i) + "\n");
//                Log.d("meds",meds.get(i));
//            }
//        }
//    }

    private void insertIntoDatabase(String name, int milligram, int time, Boolean isChecked){
        Medicine m = new Medicine();
        m.setMilligram(milligram);
        m.setName(name);
        m.setTime(time);
        m.setChecked(isChecked);

        db.medicineDAO().insertAll(m);

        Log.d("Medicine", "Naar de database geschreven");
    }

    private void handleApiCall(int id){
        int callid = id;
        NetworkManager.getInstance(this).getRequest("http://136.144.230.97:8090/api/medicationname/" + String.valueOf(callid) + "?api_token=CilZjPDfkHDmb29qcJkqBS7bB2cup9T7Onqcmfaqt027QhvpqBhFvLinJ6Dp", new VolleyCallback() {
            @Override
            public void onSuccess(String result) { //de anonieme klasse gaat nu dingen doen
                testApi testapi = gson.fromJson(result, testApi.class);
//                Log.d("tessst", "Toevoegen aan db:: " + testapi.getName());
                handleApiResult(testapi.getName());
            }
        });
    }

    private void handleApiResult(String name){
//        Log.d("tessst", "AAAAAAAAAA");
        MedicationName mName = new MedicationName();
        mName.setName(name);

        db.medicationNameDAO().insertAll(mName);
        List<MedicationName> medicationNamesFromApi= db.medicationNameDAO().loadAll();
//        Log.d("list", allMedicationNames.get(1).toString());
    }
}
