package com.example.imtpmd;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.gson.Gson;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static java.lang.Integer.parseInt;


public class MainActivity extends AppCompatActivity {
    private static MedicineViewModel medicineViewModel;
    private static MedicationNameViewModel medicationNameViewModel;

    private ListView medNameListview;
    private ArrayList<String> allMedicationNamesAL = new ArrayList<>();
   // private int firstId;
    private Intent in;


    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // DEze database wordt ook in HomeFragment aangemaakt, dus ik weet niet of we het ergens 'globaal' kunnen doen?
//         db = Room
//                .databaseBuilder(getApplicationContext(), AppDatabase.class, "medicine")
//                .allowMainThreadQueries() // Dit moet nog weg!!!
//                .build();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);
        medicationNameViewModel = ViewModelProviders.of(this).get(MedicationNameViewModel.class);

        in = new Intent(MainActivity.this, PopActivity.class);

         medNameListview = findViewById(R.id.medslistview);

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

//        insertIntoDatabase("testOchtend9", 40, getDateTime(9, 0), false);
//        insertIntoDatabase("testMiddag155", 40, getDateTime(15, 30), false);
//        insertIntoDatabase("testAvond20", 40, getDateTime(20, 18), false);
//        insertIntoDatabase("testNacht4", 40, getDateTime(4, 0), false);
//        insertIntoDatabase("testOchtend10", 40, getDateTime(10, 50), false);
//        insertIntoDatabase("testMiddag15", 40, getDateTime(15, 0), false);
//        insertIntoDatabase("testAvond22", 40, getDateTime(22, 7), false);
//        insertIntoDatabase("testNacht5", 40, getDateTime(5, 0), false);
//        insertIntoDatabase("testOchtend8", 40, getDateTime(8, 22), false);
//        insertIntoDatabase("testMiddag13", 40, getDateTime(13, 0), false);
//        insertIntoDatabase("testAvond23", 40, getDateTime(23, 31), false);
//        insertIntoDatabase("testNacht2", 40, getDateTime(2, 0), false);


        medicationNameViewModel.getAllMedicationNames().observe(this, new Observer<List<MedicationName>>() {
           @Override
           public void onChanged(@Nullable final List<MedicationName> meds){
                for (MedicationName medicationName : meds){
                    allMedicationNamesAL.add(medicationName.getName());
                    Log.d("ARRAYLIST", "onChanged: " + medicationName.getName());

                    ArrayAdapter<String> allMedicationNamesAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, allMedicationNamesAL);
                    medNameListview.setAdapter(allMedicationNamesAdapter);
                }
           }
        });

        medNameListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "Je hebt geklikt!" + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT ).show();

                in.putExtra("givenMedName", adapterView.getItemAtPosition(i).toString());

                NetworkManager.getInstance(getApplicationContext()).getRequest("http://136.144.230.97:8090/api/medicationinfo/" + adapterView.getItemAtPosition(i).toString() + "?api_token=CilZjPDfkHDmb29qcJkqBS7bB2cup9T7Onqcmfaqt027QhvpqBhFvLinJ6Dp", new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Gson gson = new Gson();
                        MedicationInfo medicationInfo = gson.fromJson(result, MedicationInfo.class);
                        in.putExtra("givenMedSort" , medicationInfo.getSort());
                        in.putExtra("givenMedUse" , medicationInfo.getUse());

                        startActivity(in);

                    }
                });

            }
        });



    }

    private Long getDateTime(int hour, int minute){
        Calendar currentTime = Calendar.getInstance();
        currentTime.set(Calendar.HOUR_OF_DAY, hour);
        currentTime.set(Calendar.MINUTE, minute);
        currentTime.set(Calendar.MILLISECOND, 0);

        Log.d("tijd", String.valueOf(currentTime.getTimeInMillis()));
        Log.d("tijd", String.valueOf(currentTime.getTime().getTime()));

        return currentTime.getTimeInMillis();
    }

    private void showStartScreen() {
        Intent myIntent = new Intent(getBaseContext(), WelcomeActivity.class);
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();

        startActivity(myIntent);
    }

    private void insertIntoDatabase(String name, int milligram, Long date, Boolean isChecked, Long dateFrom, Long dateTo){
        Medicine m = new Medicine(name, milligram, date, isChecked, dateFrom, dateTo);

        medicineViewModel.insert(m);

        Log.d("Medicine", "Naar de database geschreven");
    }

    private void handleApiCall(int id){
        int callid = id;

    }


}
